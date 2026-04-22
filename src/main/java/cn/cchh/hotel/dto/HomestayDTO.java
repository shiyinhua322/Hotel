package cn.cchh.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 民宿DTO
 */
@Data
public class HomestayDTO {

    /**
     * 民宿ID（更新时必填）
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
    private String name;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    private String address;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
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
}
