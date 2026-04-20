package cn.cchh.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GuestDTO {
    private Long id;
    
    @NotBlank(message = "姓名不能为空", groups = {AddGroup.class})
    @Size(min = 2, max = 20, message = "姓名长度必须在2到20个字符之间", groups = {AddGroup.class, UpdateGroup.class})
    private String username;
    
    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class})
    @Size(min = 11, max = 11, message = "手机号必须为11位", groups = {AddGroup.class, UpdateGroup.class})
    private String phone;
    
    private String identityCard;
    
    private String gender;
    
    private String address;
    
    @NotNull(message = "房间ID不能为空", groups = {AddGroup.class})
    private Long roomId;
    
    private String roomNumber;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime checkInTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime checkOutTime;
    
    private Integer status;
    
    private Integer current;
    
    private Integer size;
    
    public interface AddGroup {}
    public interface UpdateGroup {}
}
