package cn.cchh.hotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 注册信息存储实体
 */
@Data
public class RegisterDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String nickname;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String phone;
    
    @NotBlank(message = "用户身份不能为空")
    private String identity;

}
