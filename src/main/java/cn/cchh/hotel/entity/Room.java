package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 房间信息实体类
 * 对应数据库表：room
 * 
 * @author Hotel System
 * @since 2026-04-16
 */
@Data
@TableName("room")
public class Room {

    /**
     * 房间ID（主键，自增）
     */
    private Long id;

    /**
     * 房间号（如：101、102、201等）
     */
    private String roomNumber;

    /**
     * 房间专属名称（如：海景大床房、豪华套房A、阳光标准间等）
     */
    private String roomName;

    /**
     * 房间类型（如：标准间、大床房、套房、豪华套房等）
     */
    private String roomType;

    /**
     * 房间价格（单位：元/晚）
     */
    private BigDecimal price;

    /**
     * 容纳人数（默认2人）
     */
    private Integer capacity;

    /**
     * 房间描述（详细介绍房间的设施、特色等）
     */
    private String description;

    /**
     * 房间图片URL（多张图片用逗号分隔）
     * 示例：https://example.com/room1.jpg,https://example.com/room2.jpg
     */
    private String images;

    /**
     * 房间设施信息（JSON格式或逗号分隔）
     * 示例：WiFi,空调,电视,独立卫生间,迷你吧
     * 或 JSON：{"wifi":true,"ac":true,"tv":true}
     */
    private String facilities;

    /**
     * 房间状态
     * 0 - 不可用
     * 1 - 可用（空闲）
     * 2 - 已预订
     * 3 - 维修中
     */
    private Integer status;

    /**
     * 商家ID（关联 user 表的 id，表示该房间属于哪个商家）
     */
    private Long merchantId;

    /**
     * 房间地址（详细地址）
     */
    private String address;

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
