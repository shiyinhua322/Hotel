package cn.cchh.hotel.dto;
1

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 损耗物品类型DTO
 */
@Data
public class ConsumableTypeDTO {

    /**
     * 类型ID（更新时必填）
     */
    private Long id;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空")
    private String typeName;

    /**
     * 描述
     */
    private String description;
}
