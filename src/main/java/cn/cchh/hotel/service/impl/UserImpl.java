package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.LoginDTO;
import cn.cchh.hotel.dto.RegisterDTO;
import cn.cchh.hotel.entity.User;
import cn.cchh.hotel.mapper.UserMapper;
import cn.cchh.hotel.service.UserService;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;


@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public List<User> selectUserList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",0);
        return this.list(queryWrapper);
    }

    @Override
    public boolean register(RegisterDTO registerDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", registerDTO.getUsername());
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setNickname(registerDTO.getNickname());
        user.setPassword(DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes()));
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setIdentity(registerDTO.getIdentity());
        return this.save(user);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username", loginDTO.getUsername());
        queryWrapper1.eq("identity", loginDTO.getIdentity());
        User user = this.getOne(queryWrapper1);
        if (user == null) {
            throw new RuntimeException("用户名或身份错误");
        }
        if (user.getDeleted() == 1) {
            throw new RuntimeException("用户已删除");
        }
        String md5Password = DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        if (!md5Password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        StpUtil.login(user.getId());
        return user;
    }

    @Override
    public Page<User> getUserPage(int current, int size) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        // 创建分页对象，设置当前页和每页大小
        Page<User> page = new Page<>(current, size);
        // 执行分页查询并返回结果
        return this.page(page,queryWrapper);
    }

    @Override
    public Page<User> getUserNamePage(int current, int size, String username) {
        // 创建查询条件包装器
        QueryWrapper<User> qw = new QueryWrapper<>();
        // 添加模糊查询条件：用户名包含指定关键词
        qw.like("username", username);
        qw.eq("deleted", 0);
        // 创建分页对象，设置当前页和每页大小
        Page<User> page = new Page<>(current, size);
        // 执行分页查询并返回结果
        return this.page(page,qw);
    }

    /**
     * 修改用户基础信息
     */
    @Override
    public boolean updateUser(User user) {
        // 创建更新条件包装器
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        // 设置更新条件为用户ID
        updateWrapper.eq(User::getId, user.getId());
        updateWrapper.eq(User::getDeleted, 0);
        // 设置需要更新的字段
        updateWrapper.set(User::getUsername, user.getUsername())
                .set(User::getEmail, user.getEmail())
                .set(User::getPhone, user.getPhone())
                .set(User::getUpdateTime, new Date()); // 更新时间
        // 执行更新操作
        boolean result = this.update(updateWrapper);
        return result;
    }

    /**
     * 修改密码
     */
    @Override
    public boolean updateUserPassword(Long id, String password) {
        // 创建更新条件包装器
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        // 设置更新条件为用户 ID
        updateWrapper.eq(User::getId, id);
        updateWrapper.eq(User::getDeleted, 0);
        // 设置需要更新的字段
        updateWrapper.set(User::getPassword, DigestUtils.md5DigestAsHex(password.getBytes()));
        // 执行更新操作
        boolean result = this.update(updateWrapper);
        return result;
    }
    /**
     * 获取删除用户状态--逻辑删除
     */
    @Override
    public boolean deleteUser(Long id) {
        // 执行删除操作
        boolean result = this.removeById(id);
        return result;
    }
    /**
     * 获取修改用户删除状态--业务删除
     */
    @Override
    public boolean updateUserDeleted(Long id) {
        // 创建更新条件包装器
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        // 设置更新条件为用户ID
        updateWrapper.eq(User::getId, id);
        updateWrapper.eq(User::getDeleted, 0);
        // 设置需要更新的字段
        updateWrapper.set(User::getDeleted, 1);
        // 执行更新操作
        boolean result = this.update(updateWrapper);
        return result;
    }
    /**
     * 获取用户信息
     */
    @Override
    public User getUserById(Long id) {
        // 创建查询条件包装器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 添加查询条件：用户ID
        queryWrapper.eq("id", id);
        queryWrapper.eq("deleted", 0);
        // 执行查询操作并返回结果
        return this.getOne(queryWrapper);
    }

}
