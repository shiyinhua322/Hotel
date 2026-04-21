package cn.cchh.hotel.controller;
1
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.dto.ConsumableApplicationDTO;
import cn.cchh.hotel.entity.ConsumableApplication;
import cn.cchh.hotel.entity.ConsumableType;
import cn.cchh.hotel.entity.User;
import cn.cchh.hotel.service.ConsumableApplicationService;
import cn.cchh.hotel.service.ConsumableTypeService;
import cn.cchh.hotel.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 损耗物品申请管理控制器
 * 提供损耗物品申请管理的 RESTful API 接口
 */
@RestController
@RequestMapping("/consumable/application")
public class ConsumableApplicationController {

    @Autowired
    private ConsumableApplicationService applicationService;

    @Autowired
    private ConsumableTypeService typeService;

    @Autowired
    private UserService userService;

    /**
     * 新增申请记录
     * POST /consumable/application/add
     *
     * @param application 申请信息（JSON格式）
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody ConsumableApplication application) {
        try {
            boolean save = applicationService.save(application);
            if (save) {
                return Result.success("新增成功");
            } else {
                return Result.error("新增失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 逻辑删除申请记录
     * DELETE /consumable/application/delete/{id}
     *
     * @param id 申请ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        try {
            boolean removed = applicationService.removeById(id);
            if (removed) {
                return Result.success("删除成功");
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改申请记录
     * POST /consumable/application/update
     *
     * @param application 申请信息（必须包含ID）
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody ConsumableApplication application) {
        try {
            boolean updated = applicationService.updateById(application);
            if (updated) {
                return Result.success("修改成功");
            } else {
                return Result.error("修改失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID查询单条申请
     * GET /consumable/application/{id}
     *
     * @param id 申请ID
     * @return 申请信息（DTO格式）
     */
    @GetMapping("/{id}")
    public Result<ConsumableApplicationDTO> getById(@PathVariable Long id) {
        try {
            ConsumableApplication application = applicationService.getById(id);
            if (application == null) {
                return Result.error("未找到该记录");
            }
            ConsumableApplicationDTO dto = convertToDTO(application);
            return Result.success("查询成功", dto);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询某个商家的所有申请（不分页）
     * GET /consumable/application/list?merchantId=xxx
     *
     * @param merchantId 商家ID
     * @return 申请列表（DTO格式）
     */
    @GetMapping("/list")
    public Result<List<ConsumableApplicationDTO>> listByMerchant(@RequestParam Long merchantId) {
        try {
            LambdaQueryWrapper<ConsumableApplication> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ConsumableApplication::getMerchantId, merchantId)
                    .orderByDesc(ConsumableApplication::getApplicationDate);
            List<ConsumableApplication> list = applicationService.list(wrapper);
            List<ConsumableApplicationDTO> dtoList = list.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return Result.success("查询成功", dtoList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分页查询申请记录
     * GET /consumable/application/page?pageNum=1&pageSize=10&merchantId=xxx&typeId=xxx&startDate=xxx&endDate=xxx
     *
     * @param pageNum 当前页码，默认1
     * @param pageSize 每页条数，默认10
     * @param merchantId 商家ID（可选）
     * @param typeId 类型ID（可选）
     * @param startDate 开始日期（可选）
     * @param endDate 结束日期（可选）
     * @return 分页结果（DTO格式）
     */
    @GetMapping("/page")
    public Result<Page<ConsumableApplicationDTO>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long merchantId,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            Page<ConsumableApplication> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<ConsumableApplication> wrapper = new LambdaQueryWrapper<>();
            if (merchantId != null) {
                wrapper.eq(ConsumableApplication::getMerchantId, merchantId);
            }
            if (typeId != null) {
                wrapper.eq(ConsumableApplication::getTypeId, typeId);
            }
            if (startDate != null && !startDate.isEmpty()) {
                wrapper.ge(ConsumableApplication::getApplicationDate, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                wrapper.le(ConsumableApplication::getApplicationDate, endDate);
            }
            wrapper.orderByDesc(ConsumableApplication::getApplicationDate);
            Page<ConsumableApplication> resultPage = applicationService.page(page, wrapper);

            // 转换为DTO分页
            Page<ConsumableApplicationDTO> dtoPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
            List<ConsumableApplicationDTO> dtoList = resultPage.getRecords().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            dtoPage.setRecords(dtoList);
            return Result.success("查询成功", dtoPage);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 将实体转换为DTO，填充关联名称
     */
    private ConsumableApplicationDTO convertToDTO(ConsumableApplication application) {
        ConsumableApplicationDTO dto = new ConsumableApplicationDTO();
        BeanUtils.copyProperties(application, dto);

        // 填充类型名称
        if (application.getTypeId() != null) {
            ConsumableType type = typeService.getById(application.getTypeId());
            if (type != null) {
                dto.setTypeName(type.getTypeName());
            }
        }

        // 填充商家名称（使用username或nickname）
        if (application.getMerchantId() != null) {
            User merchant = userService.getById(application.getMerchantId());
            if (merchant != null) {
                dto.setMerchantName(merchant.getNickname() != null ? merchant.getNickname() : merchant.getUsername());
            }
        }

        return dto;
    }
}