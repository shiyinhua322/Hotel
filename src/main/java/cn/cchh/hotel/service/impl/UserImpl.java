package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.LoginDTO;
import cn.cchh.hotel.dto.RegisterDTO;
import cn.cchh.hotel.entity.User;
import cn.cchh.hotel.mapper.UserMapper;
import cn.cchh.hotel.service.UserService;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public boolean register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", registerDTO.getUsername());
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }
        // 创建新用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes()));
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        return this.save(user);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        // 查询用户
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username", loginDTO.getUsername());
        User user = this.getOne(queryWrapper1);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        //查询用户是否已删除

        // 验证密码
        String md5Password = DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        if (!md5Password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        StpUtil.login(user.getId());
        return user;
    }

}
