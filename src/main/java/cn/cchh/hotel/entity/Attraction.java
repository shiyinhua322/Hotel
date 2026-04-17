package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 景点信息实体类
 * 对应数据库表：attraction
 *
 * @author Hotel System
 * @since 2026-04-17
 */
@Data
@TableName("attraction")
public class Attraction {

    /**
     * 景点ID（主键，自增）
     */
    private Long id;

    /**
     * 景点名称
     */
    private String name;

    /**
     * 景点描述
     */
    private String description;

    /**
     * 景点地址
     */
    private String address;

    /**
     * 门票价格（单位：元）
     */
    private BigDecimal price;

    /**
     * 景点图片URL（多张图片用逗号分隔）
     */
    private String images;

    /**
     * 开放时间（如：08:00-18:00）
     */
    private String openingHours;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 评分（1.0-5.0）
     */
    private BigDecimal rating;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 是否热门
     * 0 - 否
     * 1 - 是
     */
    private Integer isHot;

    /**
     * 状态
     * 0 - 下架
     * 1 - 上架
     */
    private Integer status;

    /**
     * 排序值（数值越大越靠前）
     */
    private Integer sortOrder;

    /**
     * 逻辑删除标识
     * 0 - 未删除
     * 1 - 已删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间（自动填充）
     */
    private LocalDateTime createTime;

    /**
     * 更新时间（自动更新）
     */
    private LocalDateTime updateTime;
}
