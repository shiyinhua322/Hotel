package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.GuestDTO;
import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.entity.Guest;
import cn.cchh.hotel.service.GuestService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping("/checkin")
    public Result checkIn(@Validated(GuestDTO.AddGroup.class) @RequestBody GuestDTO guestDTO) {
        try {
            boolean result = guestService.checkIn(guestDTO);
            if (result) {
                return Result.success("入住登记成功");
            } else {
                return Result.error("入住登记失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/checkout/{guestId}")
    public Result checkOut(@PathVariable Long guestId) {
        try {
            boolean result = guestService.checkOut(guestId);
            if (result) {
                return Result.success("退房成功");
            } else {
                return Result.error("退房失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result updateGuest(@Validated(GuestDTO.UpdateGroup.class) @RequestBody GuestDTO guestDTO) {
        try {
            boolean result = guestService.updateGuest(guestDTO);
            if (result) {
                return Result.success("客人信息更新成功");
            } else {
                return Result.error("客人信息更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteGuest(@PathVariable Long id) {
        try {
            boolean result = guestService.deleteGuest(id);
            if (result) {
                return Result.success("删除成功");
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Guest> getGuestById(@PathVariable Long id) {
        try {
            Guest guest = guestService.getGuestById(id);
            if (guest != null) {
                return Result.success("查询成功", guest);
            } else {
                return Result.error("客人不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/page")
    public Result<Page<Guest>> getGuestPage(@RequestBody(required = false) GuestDTO guestDTO) {
        try {
            if (guestDTO == null) {
                guestDTO = new GuestDTO();
            }
            Page<Guest> page = guestService.getGuestPage(
                    guestDTO.getCurrent(),
                    guestDTO.getSize(),
                    guestDTO.getUsername(),
                    guestDTO.getPhone(),
                    guestDTO.getRoomId(),
                    guestDTO.getStatus()
            );
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/search")
    public Result<List<Guest>> searchGuests(@RequestBody(required = false) GuestDTO guestDTO) {
        try {
            if (guestDTO == null) {
                guestDTO = new GuestDTO();
            }
            List<Guest> guests = guestService.searchGuests(
                    guestDTO.getUsername(),
                    guestDTO.getPhone(),
                    guestDTO.getRoomId(),
                    guestDTO.getStatus()
            );
            return Result.success("查询成功", guests);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/current")
    public Result<List<Guest>> getCurrentGuests() {
        try {
            List<Guest> guests = guestService.getCurrentGuests();
            return Result.success("查询成功", guests);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/room/{roomId}")
    public Result<List<Guest>> getGuestsByRoomId(@PathVariable Long roomId) {
        try {
            List<Guest> guests = guestService.getGuestsByRoomId(roomId);
            return Result.success("查询成功", guests);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<Guest>> getAllGuests() {
        try {
            Page<Guest> page = guestService.getGuestPage(1, 1000, null, null, null, null);
            return Result.success("查询成功", page.getRecords());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
