package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 民宿实体类
 */
@Data
@TableName("homestay")
public class Homestay {
    /**
     * 民宿ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 商家ID（关联user.id）
     */
    private Long merchantId;
    
    /**
     * 民宿名称
     */
    private String name;
    
    /**
     * 详细地址
     */
    private String address;
    
    /**
     * 联系电话
     */
    private String contactPhone;
    
    /**
     * 详细介绍
     */
    private String description;
    
    /**
     * 封面图URL
     */
    private String coverImage;
    
    /**
     * 状态：1-营业中，0-暂停营业
     */
    private Integer status;
    
    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;
}
