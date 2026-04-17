package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 房型实体类
 */
@Data
@TableName("room_type")
public class RoomType {
    /**
     * 房间类型ID
     */
    @TableId(type = IdType.AUTO)
    private Integer typeId;
    
    /**
     * 房间类型名称
     */
    private String typeName;
    
    /**
     * 价格（元/晚）
     */
    private BigDecimal price;
    
    /**
     * 房间总数
     */
    private Integer totalRooms;
    
    /**
     * 简介
     */
    private String intro;
    
    /**
     * 封面图URL
     */
    private String coverUrl;
    
    /**
     * 详细描述/设施信息
     */
    private String detail;
    
    /**
     * 状态：0-上架，1-下架
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
