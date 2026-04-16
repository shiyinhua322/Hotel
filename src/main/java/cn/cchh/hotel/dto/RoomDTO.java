package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 房间信息数据传输对象（DTO）
 * 用于房间的新增和修改操作
 * 
 * @author Hotel System
 * @since 2026-04-16
 */
@Data
public class RoomDTO {

    /**
     * 房间ID（修改时必填，新增时为空）
     */
    private Long id;

    /**
     * 房间号（必填）
     */
    @NotBlank(message = "房间号不能为空")
    private String roomNumber;

    /**
     * 房间类型（必填）
     */
    @NotBlank(message = "房间类型不能为空")
    private String roomType;

    /**
     * 房间价格（必填，单位：元/晚）
     */
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    /**
     * 容纳人数（必填）
     */
    @NotNull(message = "容纳人数不能为空")
    private Integer capacity;

    /**
     * 房间描述（可选）
     */
    private String description;

    /**
     * 房间图片URL（可选，多张用逗号分隔）
     */
    private String images;

    /**
     * 房间设施信息（可选）
     */
    private String facilities;

    /**
     * 房间状态（可选，默认为1-可用）
     */
    private Integer status;

    /**
     * 商家ID（可选，表示房间所属商家）
     */
    private Long merchantId;

    /**
     * 房间地址（可选）
     */
    private String address;
}
