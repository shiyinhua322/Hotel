package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 房间信息DTO
 */
@Data
public class RoomDTO {

    private Long id;

    @NotBlank(message = "房间号不能为空")
    private String roomNumber;

    @NotBlank(message = "房间类型不能为空")
    private String roomType;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @NotNull(message = "容纳人数不能为空")
    private Integer capacity;

    private String description;

    private String images;

    private String facilities;

    private Integer status;

    private Long merchantId;

    private String address;
}
