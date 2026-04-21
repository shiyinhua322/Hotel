package cn.cchh.hotel.dto;
1
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ConsumableApplicationDTO {
    private Long id;
    private Long merchantId;
    private String merchantName;      // 商家用户名或昵称
    private Long homestayId;
    private String homestayName;      // 民宿名称
    private Long typeId;
    private String typeName;          // 损耗物品类型名称
    private String itemName;
    private Integer quantity;
    private String unit;
    private LocalDate applicationDate;
    private String reason;
    private LocalDateTime createTime;
}