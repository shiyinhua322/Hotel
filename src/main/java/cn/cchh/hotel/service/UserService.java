package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.LoginDTO;
import cn.cchh.hotel.dto.RegisterDTO;
import cn.cchh.hotel.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {

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
}
