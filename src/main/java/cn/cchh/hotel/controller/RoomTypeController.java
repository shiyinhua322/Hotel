
package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.dto.RoomTypeDTO;
import cn.cchh.hotel.dto.RoomTypeQueryDTO;
import cn.cchh.hotel.entity.RoomType;
import cn.cchh.hotel.service.RoomTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    /**
     * 创建房间类型
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
     * 更新房间类型
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
     * 删除房间类型
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
     * 根据ID查询房间类型
     */
    @GetMapping("/detail/{id}")
    public Result getRoomTypeById(@PathVariable Long id) {
        try {
            RoomType roomType = roomTypeService.getRoomTypeById(id);
            return Result.success("查询成功", roomType);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分页查询房间类型
     */
    @PostMapping("/query")
    public Result queryRoomTypes(@RequestBody RoomTypeQueryDTO queryDTO) {
        try {
            IPage<RoomType> page = roomTypeService.queryRoomTypes(queryDTO);
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据民宿ID查询房间类型列表
     */
    @GetMapping("/list/{homestayId}")
    public Result getRoomTypesByHomestayId(
            @PathVariable Long homestayId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            IPage<RoomType> page = roomTypeService.getRoomTypesByHomestayId(homestayId, pageNum, pageSize);
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
