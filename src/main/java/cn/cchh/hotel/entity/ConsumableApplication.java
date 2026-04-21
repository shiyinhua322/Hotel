package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("consumable_application")
public class ConsumableApplication {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long merchantId;

    private Long homestayId;

    private Long typeId;

    private String itemName;

    private Integer quantity;

    private String unit;

    private LocalDate applicationDate;

    private String reason;

    @TableLogic
    private Integer deleted;

}