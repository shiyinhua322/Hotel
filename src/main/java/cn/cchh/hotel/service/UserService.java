package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.LoginDTO;
import cn.cchh.hotel.dto.RegisterDTO;
import cn.cchh.hotel.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface UserService extends IService<User> {

    /**
     * 获取删除用户状态--逻辑删除
     * @param id 索引 id
     * @return
     */
    boolean deleteUser(Long id);

    /**
     * 获取修改用户删除状态--业务删除
     * @param id 索引 id
     * @return
     */
    boolean updateUserDeleted(Long id);

    /**
     * 获取用户信息修改状态
     *
     * @param user 用户对象
     * @return
     */
    boolean updateUser(User user);

    /**
     * 获取修改密码状态
     * @param id 索引
     * @param password 密码
     * @return
     */
    boolean updateUserPassword(Long id, String password);

    /**
     * 分页查询---模糊查询用户名称
     * @param current 页数
     * @param size 当前页数据行数
     * @param username 用户名称
     * @return
     */
    Page<User> getUserNamePage(int current, int size, String username);

    /**
     * 分页查询用户
     * @param current 页数
     * @param size 当前页数据行数
     * @return
     */
    Page<User> getUserPage(int current, int size);

    /**
     * 测试数据访问
     * @return
     */
    List<User> selectUserList();

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 是否成功
     */
    boolean register(RegisterDTO registerDTO);

    /**
     * 用户登录
     * @param loginDTO 登录信息
     * @return token
     */
    User login(LoginDTO loginDTO);
    /**
     * 获取用户信息
     * @param id 索引
     * @return 用户信息
     */
    User getUserById(Long id);
}
