package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.dto.RoomTypeDTO;
import cn.cchh.hotel.entity.RoomType;
import cn.cchh.hotel.service.RoomTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 房型控制器
 */
@RestController
@RequestMapping("/roomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    /**
     * 创建房型
     */
    @PostMapping("/create")
    public Result createRoomType(@Validated @RequestBody RoomTypeDTO roomTypeDTO) {
        try {
            boolean result = roomTypeService.createRoomType(roomTypeDTO);
            if (result) {
                return Result.success("创建成功", null);
            } else {
                return Result.error("创建失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新房型
     */
    @PostMapping("/update")
    public Result updateRoomType(@Validated @RequestBody RoomTypeDTO roomTypeDTO) {
        try {
            boolean result = roomTypeService.updateRoomType(roomTypeDTO);
            if (result) {
                return Result.success("更新成功", null);
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除房型
     */
    @GetMapping("/delete/{id}")
    public Result deleteRoomType(@PathVariable Long id) {
        try {
            boolean result = roomTypeService.deleteRoomType(id);
            if (result) {
                return Result.success("删除成功", null);
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分页查询房型
     */
    @GetMapping("/query")
    public Result queryRoomTypes(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            IPage<RoomType> page = roomTypeService.queryRoomTypes(pageNum, pageSize);
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
