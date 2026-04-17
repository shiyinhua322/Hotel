package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 房间信息数据传输对象（DTO）
 * 用于房间的新增、修改和查询操作
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
     * 房间号（新增/修改时必填，查询时可选）
     */
    @NotBlank(message = "房间号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String roomNumber;

    /**
     * 房间专属名称（可选，如：海景大床房、豪华套房A等）
     */
    private String roomName;

    /**
     * 房间类型（新增/修改时必填，查询时可选）
     */
    @NotBlank(message = "房间类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String roomType;

    /**
     * 房间价格（新增/修改时必填，单位：元/晚）
     */
    @NotNull(message = "价格不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal price;

    /**
     * 容纳人数（新增/修改时必填）
     */
    @NotNull(message = "容纳人数不能为空", groups = {AddGroup.class, UpdateGroup.class})
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

    // ==================== 查询专用字段 ====================

    /**
     * 最低价格（查询时使用，价格区间查询）
     */
    private BigDecimal minPrice;

    /**
     * 最高价格（查询时使用，价格区间查询）
     */
    private BigDecimal maxPrice;

    /**
     * 当前页码（查询时使用，默认第1页）
     */
    private Integer current = 1;

    /**
     * 每页大小（查询时使用，默认10条）
     */
    private Integer size = 10;

    /**
     * 新增验证组
     */
    public interface AddGroup {}

    /**
     * 修改验证组
     */
    public interface UpdateGroup {}
}
