
package cn.cchh.hotel.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomTypeQueryDTO {

    private Long homestayId;

    private String typeName;

    private String status;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
