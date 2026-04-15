package cn.cchh.hotel.controller;

import cn.cchh.hotel.entity.dto.Result;
import cn.cchh.hotel.entity.dto.LoginDTO;
import cn.cchh.hotel.entity.dto.RegisterDTO;
import cn.cchh.hotel.service.UserService;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result register(@Validated @RequestBody RegisterDTO registerDTO) {
        try {
            boolean result = userService.register(registerDTO);
            if (result) {
                return Result.success("注册成功");
            } else {
                return Result.error("注册失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            String token = userService.login(loginDTO);
            Map<String,String> map = new HashMap<>();
            map.put("username",loginDTO.getUsername());
            map.put("token",StpUtil.getTokenValue());
            return Result.success("登录成功",map);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    // 测试注销
    @GetMapping ("logout")
    public Result logout() {
        // 获取当前登录的 tokenValue
        String tokenValue = StpUtil.getTokenValue();
        if (tokenValue!=null){
            StpUtil.logout(); // 直接注销当前账号
            return Result.success("测试注销");
        }else {
            return Result.error("账号未登");
        }
    }
}
