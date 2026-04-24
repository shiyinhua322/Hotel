package cn.cchh.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 民宿认证申请表实体类
 */
@Data
@TableName("homestay_certification")
public class HomestayCertification {
    /**
     * 认证ID
     */
    //1
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 商家ID（关联user.id）
     */
    private Long merchantId;
    
    /**
     * 民宿名称
     */
    private String homestayName;
    
    /**
     * 民宿地址
     */
    private String address;
    
    /**
     * 联系电话
     */
    private String contactPhone;
    
    /**
     * 民宿简介
     */
    private String description;
    
    /**
     * 商家身份证号
     */
    private String idCardNumber;
    
    /**
     * 营业执照图片URL
     */
    private String businessLicenseImage;
    
    /**
     * 审核状态：pending-待审核，approved-已通过，rejected-已驳回
     */
    private String status;
    
    /**
     * 驳回原因
     */
    private String rejectReason;
    
    /**
     * 提交时间
     */
    private String submitTime;
    
    /**
     * 审核时间
     */
    private String reviewTime;
    
    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;
}
