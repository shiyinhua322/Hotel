package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.dto.RoomDTO;
import cn.cchh.hotel.entity.Room;
import cn.cchh.hotel.service.RoomService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房间管理控制器
 * 提供房间管理的 RESTful API 接口
 * 
 * @author Hotel System
 * @since 2026-04-16
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * 添加房间
     * POST /room/add
     *
     * @param roomDTO 房间信息（JSON格式）
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result addRoom(@Validated(RoomDTO.AddGroup.class) @RequestBody RoomDTO roomDTO) {
        try {
            boolean result = roomService.addRoom(roomDTO);
            if (result) {
                return Result.success("房间添加成功");
            } else {
                return Result.error("房间添加失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新房间信息
     * POST /room/update
     *
     * @param roomDTO 房间信息（必须包含房间ID）
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result updateRoom(@Validated(RoomDTO.UpdateGroup.class) @RequestBody RoomDTO roomDTO) {
        try {
            boolean result = roomService.updateRoom(roomDTO);
            if (result) {
                return Result.success("房间更新成功");
            } else {
                return Result.error("房间更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除房间（逻辑删除）
     * DELETE /room/delete/{id}
     *
     * @param id 房间ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteRoom(@PathVariable Long id) {
        try {
            boolean result = roomService.deleteRoom(id);
            if (result) {
                return Result.success("房间删除成功");
            } else {
                return Result.error("房间删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分页查询房间
     * POST /room/page
     *
     * @param roomDTO 查询参数（JSON格式，包含分页和筛选条件）
     * @return 分页结果
     */
    @PostMapping("/page")
    public Result<Page<Room>> getRoomPage(@RequestBody(required = false) RoomDTO roomDTO) {
        try {
            if (roomDTO == null) {
                roomDTO = new RoomDTO();
            }
            Page<Room> page = roomService.getRoomPage(
                roomDTO.getCurrent(), 
                roomDTO.getSize(), 
                roomDTO.getRoomNumber(),
                roomDTO.getRoomName(),
                roomDTO.getRoomType(), 
                roomDTO.getMinPrice(), 
                roomDTO.getMaxPrice(), 
                roomDTO.getCapacity(), 
                roomDTO.getStatus(), 
                roomDTO.getMerchantId(), 
                roomDTO.getAddress()
            );
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据商家ID获取该商家的所有房间
     * GET /room/merchant/{merchantId}
     *
     * @param merchantId 商家ID
     * @return 房间列表
     */
    @GetMapping("/merchant/{merchantId}")
    public Result<List<Room>> getRoomsByMerchantId(@PathVariable Long merchantId) {
        try {
            List<Room> rooms = roomService.getRoomsByMerchantId(merchantId);
            return Result.success("查询成功", rooms);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据状态获取房间列表
     * GET /room/status/{status}
     *
     * @param status 房间状态（0-不可用，1-可用，2-已预订，3-维修中）
     * @return 房间列表
     */
    @GetMapping("/status/{status}")
    public Result<List<Room>> getRoomsByStatus(@PathVariable Integer status) {
        try {
            List<Room> rooms = roomService.getRoomsByStatus(status);
            return Result.success("查询成功", rooms);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新房间状态
     * POST /room/status/update?id=1&status=2
     *
     * @param id 房间ID
     * @param status 新状态
     * @return 操作结果
     */
    @PostMapping("/status/update")
    public Result updateRoomStatus(@RequestParam Long id, @RequestParam Integer status) {
        try {
            boolean result = roomService.updateRoomStatus(id, status);
            if (result) {
                return Result.success("状态更新成功");
            } else {
                return Result.error("状态更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 多条件搜索房间
     * POST /room/search
     *
     * @param roomDTO 查询参数（JSON格式）
     * @return 房间列表
     */
    @PostMapping("/search")
    public Result<List<Room>> searchRooms(@RequestBody(required = false) RoomDTO roomDTO) {
        try {
            if (roomDTO == null) {
                roomDTO = new RoomDTO();
            }
            List<Room> rooms = roomService.searchRooms(
                roomDTO.getRoomNumber(),
                roomDTO.getRoomName(),
                roomDTO.getRoomType(), 
                roomDTO.getMinPrice(), 
                roomDTO.getMaxPrice(), 
                roomDTO.getCapacity(), 
                roomDTO.getStatus(), 
                roomDTO.getMerchantId(), 
                roomDTO.getAddress()
            );
            return Result.success("查询成功", rooms);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取所有房间列表（不分页，最多1000条）
     * GET /room/list
     *
     * @return 房间列表
     */
    @GetMapping("/list")
    public Result<List<Room>> getAllRooms() {
        try {
            Page<Room> page = roomService.getRoomPage(1, 1000, null, null, null, null, null, null, null, null, null);
            return Result.success("查询成功", page.getRecords());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
