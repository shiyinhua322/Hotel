package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 景点信息数据传输对象（DTO）
 * 用于景点的新增、修改和查询操作
 *
 * @author Hotel System
 * @since 2026-04-17
 */
@Data
public class AttractionDTO {

    /**
     * 景点ID（修改时必填，新增时为空）
     */
    private Long id;

    /**
     * 景点名称（新增/修改时必填）
     */
    @NotBlank(message = "景点名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 景点描述（可选）
     */
    private String description;

    /**
     * 景点地址（可选）
     */
    private String address;

    /**
     * 门票价格（新增/修改时必填，单位：元）
     */
    @NotNull(message = "门票价格不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal price;

    /**
     * 景点图片URL（可选，多张用逗号分隔）
     */
    private String images;

    /**
     * 开放时间（可选）
     */
    private String openingHours;

    /**
     * 联系电话（可选）
     */
    private String contactPhone;

    /**
     * 评分（可选，默认5.0）
     */
    private BigDecimal rating;

    /**
     * 是否热门（可选，默认0）
     */
    private Integer isHot;

    /**
     * 状态（可选，默认1-上架）
     */
    private Integer status;

    /**
     * 排序值（可选，默认0）
     */
    private Integer sortOrder;

    // ==================== 查询专用字段 ====================

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
