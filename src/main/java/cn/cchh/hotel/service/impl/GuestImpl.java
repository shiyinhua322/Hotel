package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.GuestDTO;
import cn.cchh.hotel.entity.Guest;
import cn.cchh.hotel.entity.Room;
import cn.cchh.hotel.mapper.GuestMapper;
import cn.cchh.hotel.service.GuestService;
import cn.cchh.hotel.service.RoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuestImpl extends ServiceImpl<GuestMapper, Guest> implements GuestService {

    @Autowired
    private RoomService roomService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkIn(GuestDTO guestDTO) {
        Room room = roomService.getById(guestDTO.getRoomId());
        if (room == null || room.getDeleted() == 1) {
            throw new RuntimeException("房间不存在");
        }

        if (room.getStatus() != 1) {
            throw new RuntimeException("房间当前不可用，状态：" + getStatusText(room.getStatus()));
        }

        Guest guest = new Guest();
        BeanUtils.copyProperties(guestDTO, guest);
        guest.setRoomNumber(room.getRoomNumber());

        if (guest.getCheckInTime() == null) {
            guest.setCheckInTime(LocalDateTime.now());
        }

        guest.setStatus(1);
        guest.setDeleted(0);

        boolean saved = this.save(guest);

        if (saved) {
            roomService.updateRoomStatus(room.getId(), 2);
        }

        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOut(Long guestId) {
        Guest guest = this.getById(guestId);
        if (guest == null || guest.getDeleted() == 1) {
            throw new RuntimeException("入住记录不存在");
        }

        if (guest.getStatus() != 1) {
            throw new RuntimeException("该客人已退房");
        }

        UpdateWrapper<Guest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", guestId);
        updateWrapper.set("check_out_time", LocalDateTime.now());
        updateWrapper.set("status", 2);

        boolean updated = this.update(updateWrapper);

        if (updated) {
            roomService.updateRoomStatus(guest.getRoomId(), 1);
        }

        return updated;
    }

    @Override
    public boolean updateGuest(GuestDTO guestDTO) {
        if (guestDTO.getId() == null) {
            throw new RuntimeException("客人ID不能为空");
        }

        Guest existGuest = this.getById(guestDTO.getId());
        if (existGuest == null || existGuest.getDeleted() == 1) {
            throw new RuntimeException("客人不存在");
        }

        Guest guest = new Guest();
        BeanUtils.copyProperties(guestDTO, guest);

        UpdateWrapper<Guest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", guestDTO.getId());
        updateWrapper.eq("deleted", 0);

        return this.update(guest, updateWrapper);
    }

    @Override
    public boolean deleteGuest(Long id) {
        UpdateWrapper<Guest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("deleted", 1);
        return this.update(updateWrapper);
    }

    @Override
    public Page<Guest> getGuestPage(Integer current, Integer size, String username,
                                    String phone, Long roomId, Integer status) {
        Page<Guest> page = new Page<>(current != null ? current : 1, size != null ? size : 10);

        QueryWrapper<Guest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);

        if (StringUtils.hasText(username)) {
            queryWrapper.like("username", username.trim());
        }
        if (StringUtils.hasText(phone)) {
            queryWrapper.like("phone", phone.trim());
        }
        if (roomId != null) {
            queryWrapper.eq("room_id", roomId);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        queryWrapper.orderByDesc("id");

        return this.page(page, queryWrapper);
    }

    @Override
    public List<Guest> searchGuests(String username, String phone, Long roomId, Integer status) {
        QueryWrapper<Guest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);

        if (StringUtils.hasText(username)) {
            queryWrapper.like("username", username.trim());
        }
        if (StringUtils.hasText(phone)) {
            queryWrapper.like("phone", phone.trim());
        }
        if (roomId != null) {
            queryWrapper.eq("room_id", roomId);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        queryWrapper.orderByDesc("id");

        return this.list(queryWrapper);
    }

    @Override
    public Guest getGuestById(Long id) {
        QueryWrapper<Guest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("deleted", 0);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Guest> getCurrentGuests() {
        QueryWrapper<Guest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    @Override
    public List<Guest> getGuestsByRoomId(Long roomId) {
        QueryWrapper<Guest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    private String getStatusText(Integer status) {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "不可用";
            case 1: return "可用";
            case 2: return "已预订";
            case 3: return "维修中";
            default: return "未知";
        }
    }
}
