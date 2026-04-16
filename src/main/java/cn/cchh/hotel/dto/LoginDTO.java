package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录信息存储实体
 */
@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "用户身份不能为空")
    private String identity;
    1
}