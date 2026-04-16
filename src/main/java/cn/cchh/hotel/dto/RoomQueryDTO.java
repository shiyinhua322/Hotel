package cn.cchh.hotel.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 房间查询条件DTO
 */
@Data
public class RoomQueryDTO {

    private String roomNumber;

    private String roomType;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer capacity;

    private Integer status;

    private Long merchantId;

    private String address;

    private Integer current = 1;

    private Integer size = 10;
}
