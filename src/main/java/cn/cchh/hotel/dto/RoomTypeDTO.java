package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 房型DTO
 */
@Data
public class RoomTypeDTO {

    /**
     * 房型ID（更新时必填）
     */
    private Long id;

    /**
     * 房型名称
     */
    @NotBlank(message = "房型名称不能为空")
    private String typeName;

    /**
     * 房型最大人数
     */
    @NotNull(message = "房型最大人数不能为空")
    private Integer capacity;

    /**
     * 业务删除标识（0-未删除，1-已删除）
     */
    private Integer delete;
}
