package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.LoginDTO;
import cn.cchh.hotel.dto.RegisterDTO;
import cn.cchh.hotel.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 测试数据访问
     * @return
     */
    List<User> selectUserList();
    /**
     * 用户注册
     */
    boolean register(RegisterDTO registerDTO);

    /**
     * 用户登录
     */
    String login(LoginDTO loginDTO);

    /**
     * 分页查询---无条件查询
     * @param current 页数
     * @param size 当前也数据行数
     * @return
     */
    Page<User> getUserPage(int current, int size);

    /**
     * 获取用户分页信息
     * @param current 当前页码
     * @param size 每页大小
     * @param username 用户名关键词
     * @return 用户分页数据
     */
    Page<User> getUserNamePage(int current, int size,String username);

    /**
     * 获取用户信息修改状态
     * @param user 用户对象
     * @return
     */
    boolean updateUser(User user);
    /**
     * 获取修改密码状态
     * @param id 索引
     * @param password 用户对象
     * @return
     */

}
