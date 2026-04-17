package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 房型DTO
 */
@Data
public class RoomTypeDTO {

    /**
     * 房间类型ID（更新时必填）
     */
    private Integer typeId;

    /**
     * 房间类型名称
     */
    @NotBlank(message = "房间类型名称不能为空")
    private String typeName;

    /**
     * 价格（元/晚）
     */
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    /**
     * 房间总数
     */
    @NotNull(message = "房间总数不能为空")
    private Integer totalRooms;

    /**
     * 简介
     */
    private String intro;

    /**
     * 封面图URL
     */
    private String coverUrl;

    /**
     * 详细描述/设施信息
     */
    private String detail;

    /**
     * 状态：0-上架，1-下架
     */
    private Integer status;
}
