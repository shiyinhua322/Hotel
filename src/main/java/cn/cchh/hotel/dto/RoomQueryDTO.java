package cn.cchh.hotel.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 房间查询条件数据传输对象（DTO）
 * 用于房间的多条件查询和分页
 * 
 * @author Hotel System
 * @since 2026-04-16
 */
@Data
public class RoomQueryDTO {

    /**
     * 房间号（支持模糊查询）
     */
    private String roomNumber;

    /**
     * 房间类型（精确匹配）
     */
    private String roomType;

    /**
     * 最低价格（价格区间查询）
     */
    private BigDecimal minPrice;

    /**
     * 最高价格（价格区间查询）
     */
    private BigDecimal maxPrice;

    /**
     * 容纳人数（最少容纳人数）
     */
    private Integer capacity;

    /**
     * 房间状态（0-不可用，1-可用，2-已预订，3-维修中）
     */
    private Integer status;

    /**
     * 商家ID（查询指定商家的房间）
     */
    private Long merchantId;

    /**
     * 地址（支持模糊查询）
     */
    private String address;

    /**
     * 当前页码（默认第1页）
     */
    private Integer current = 1;

    /**
     * 每页大小（默认10条）
     */
    private Integer size = 10;
}
