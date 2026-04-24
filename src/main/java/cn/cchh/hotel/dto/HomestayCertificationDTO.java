package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 民宿认证申请DTO
 */
@Data
public class HomestayCertificationDTO {
//1
    /**
     * 认证ID（更新时必填）
     */
    private Long id;

    /**
     * 商家ID
     */
    @NotNull(message = "商家ID不能为空")
    private Long merchantId;

    /**
     * 民宿名称
     */
    @NotBlank(message = "民宿名称不能为空")
    private String homestayName;

    /**
     * 民宿地址
     */
    @NotBlank(message = "民宿地址不能为空")
    private String address;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
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
     * 审核时间
     */
    private String reviewTime;
}
