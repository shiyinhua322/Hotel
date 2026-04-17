package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 房型实体类
 */
@Data
@TableName("room_type")
public class RoomType {
    /**
     * 房型ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 房型名称
     */
    private String typeName;
    /**
     * 房型最大人数
     */
    private Integer capacity;
    /**
     * 业务删除
     */
    private Integer delete;
}
