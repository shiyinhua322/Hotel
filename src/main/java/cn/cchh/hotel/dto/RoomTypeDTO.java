
package cn.cchh.hotel.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomTypeDTO {

    private Long id;

    @NotNull(message = "民宿ID不能为空")
    private Long homestayId;

    @NotBlank(message = "房间类型名称不能为空")
    private String typeName;

    private String intro;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "房间总数不能为空")
    private Integer totalRooms;

    private Integer leftRooms;

    private String coverUrl;

    private String detail;

    private String status;
}
