package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.dto.RoomDTO;
import cn.cchh.hotel.dto.RoomQueryDTO;
import cn.cchh.hotel.entity.Room;
import cn.cchh.hotel.service.RoomService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房间管理控制器
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * 添加房间
     */
    @PostMapping("/add")
    public Result addRoom(@Validated @RequestBody RoomDTO roomDTO) {
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
     */
    @PostMapping("/update")
    public Result updateRoom(@Validated @RequestBody RoomDTO roomDTO) {
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
     * 删除房间
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
     * 根据ID获取房间信息
     */
    @GetMapping("/{id}")
    public Result<Room> getRoomById(@PathVariable Long id) {
        try {
            Room room = roomService.getRoomById(id);
            if (room != null) {
                return Result.success("查询成功", room);
            } else {
                return Result.error("房间不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分页查询房间
     */
    @PostMapping("/page")
    public Result<Page<Room>> getRoomPage(@RequestBody RoomQueryDTO queryDTO) {
        try {
            Page<Room> page = roomService.getRoomPage(queryDTO);
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据商家ID获取房间列表
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
     * 搜索房间（多条件）
     */
    @PostMapping("/search")
    public Result<List<Room>> searchRooms(@RequestBody RoomQueryDTO queryDTO) {
        try {
            List<Room> rooms = roomService.searchRooms(queryDTO);
            return Result.success("查询成功", rooms);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取所有房间列表
     */
    @GetMapping("/list")
    public Result<List<Room>> getAllRooms() {
        try {
            RoomQueryDTO queryDTO = new RoomQueryDTO();
            queryDTO.setCurrent(1);
            queryDTO.setSize(1000);
            Page<Room> page = roomService.getRoomPage(queryDTO);
            return Result.success("查询成功", page.getRecords());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
