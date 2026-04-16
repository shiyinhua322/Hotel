package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Date;
import jakarta.validation.constraints.*;

@Data
@TableName("user")
public class User {
    private Long id;
    
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 25, message = "用户名长度必须在4到25个字符之间")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    private String avatarUrl;

    private String nickname;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Size(min = 11, max = 11, message = "手机号使用11位长度")
    private String phone;
    
    private String identity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private int deleted;
}
