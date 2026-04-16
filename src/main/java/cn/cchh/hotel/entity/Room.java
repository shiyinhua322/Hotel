package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 房间信息实体
 */
@Data
@TableName("room")
public class Room {

    private Long id;

    private String roomNumber;

    private String roomType;

    private BigDecimal price;

    private Integer capacity;

    private String description;

    private String images;

    private String facilities;

    private Integer status;

    private Long merchantId;

    private String address;

    @TableLogic
    private Integer deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
