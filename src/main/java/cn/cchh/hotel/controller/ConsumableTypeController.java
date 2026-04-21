package cn.cchh.hotel.controller;
1
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.entity.ConsumableType;
import cn.cchh.hotel.service.ConsumableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumable/type")
public class ConsumableTypeController {

    @Autowired
    private ConsumableTypeService consumableTypeService;

    /**
     * 新增损耗物品类型
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody ConsumableType consumableType) {
        boolean save = consumableTypeService.save(consumableType);
        return save ? Result.success() : Result.error("新增失败");
    }

    /**
     * 删除损耗物品类型（逻辑删除）
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        boolean removed = consumableTypeService.removeById(id);
        return removed ? Result.success() : Result.error("删除失败");
    }

    /**
     * 修改损耗物品类型
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody ConsumableType consumableType) {
        boolean updated = consumableTypeService.updateById(consumableType);
        return updated ? Result.success() : Result.error("修改失败");
    }

    /**
     * 根据ID查询单个类型
     */
    @GetMapping("/{id}")
    public Result<ConsumableType> getById(@PathVariable Long id) {
        ConsumableType type = consumableTypeService.getById(id);
        return type != null ? Result.success(type) : Result.error("未找到该类型");
    }

    /**
     * 查询所有未删除的类型（不分页）
     */
    @GetMapping("/list")
    public Result<List<ConsumableType>> listAll() {
        LambdaQueryWrapper<ConsumableType> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(ConsumableType::getTypeName);
        List<ConsumableType> list = consumableTypeService.list(wrapper);
        return Result.success(list);
    }

    /**
     * 分页查询损耗物品类型
     * @param pageNum 当前页码，默认1
     * @param pageSize 每页条数，默认10
     * @param typeName 类型名称（可选模糊查询）
     */
    @GetMapping("/page")
    public Result<Page<ConsumableType>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String typeName) {
        Page<ConsumableType> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ConsumableType> wrapper = new LambdaQueryWrapper<>();
        if (typeName != null && !typeName.isEmpty()) {
            wrapper.like(ConsumableType::getTypeName, typeName);
        }
        wrapper.orderByAsc(ConsumableType::getTypeName);
        Page<ConsumableType> result = consumableTypeService.page(page, wrapper);
        return Result.success(result);
    }
}