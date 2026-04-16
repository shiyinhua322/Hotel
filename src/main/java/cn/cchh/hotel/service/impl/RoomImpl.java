
package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.RoomDTO;
import cn.cchh.hotel.dto.RoomQueryDTO;
import cn.cchh.hotel.entity.Room;
import cn.cchh.hotel.mapper.RoomMapper;
import cn.cchh.hotel.service.RoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Override
    public boolean addRoom(RoomDTO roomDTO) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_number", roomDTO.getRoomNumber());
        queryWrapper.eq("deleted", 0);
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("房间号已存在");
        }

        Room room = new Room();
        BeanUtils.copyProperties(roomDTO, room);
        room.setStatus(room.getStatus() != null ? room.getStatus() : 1);
        room.setDeleted(0);
        room.setCreateTime(LocalDateTime.now());
        room.setUpdateTime(LocalDateTime.now());

        return this.save(room);
    }

    @Override
    public boolean updateRoom(RoomDTO roomDTO) {
        if (roomDTO.getId() == null) {
            throw new RuntimeException("房间ID不能为空");
        }

        Room existRoom = this.getById(roomDTO.getId());
        if (existRoom == null || existRoom.getDeleted() == 1) {
            throw new RuntimeException("房间不存在");
        }

        Room room = new Room();
        BeanUtils.copyProperties(roomDTO, room);
        room.setUpdateTime(LocalDateTime.now());

        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", roomDTO.getId());
        updateWrapper.eq("deleted", 0);

        return this.update(room, updateWrapper);
    }

    @Override
    public boolean deleteRoom(Long id) {
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("deleted", 1);
        updateWrapper.set("update_time", LocalDateTime.now());
        return this.update(updateWrapper);
    }

    @Override
    public Room getRoomById(Long id) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("deleted", 0);
        return this.getOne(queryWrapper);
    }

    @Override
    public Page<Room> getRoomPage(RoomQueryDTO queryDTO) {
        Page<Room> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        QueryWrapper<Room> queryWrapper = buildQueryWrapper(queryDTO);
        queryWrapper.orderByDesc("create_time");
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Room> getRoomsByMerchantId(Long merchantId) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id", merchantId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    @Override
    public List<Room> getRoomsByStatus(Integer status) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    @Override
    public boolean updateRoomStatus(Long id, Integer status) {
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.eq("deleted", 0);
        updateWrapper.set("status", status);
        updateWrapper.set("update_time", LocalDateTime.now());
        return this.update(updateWrapper);
    }

    @Override
    public List<Room> searchRooms(RoomQueryDTO queryDTO) {
        QueryWrapper<Room> queryWrapper = buildQueryWrapper(queryDTO);
        queryWrapper.orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    /**
     * 构建查询条件
     */
    private QueryWrapper<Room> buildQueryWrapper(RoomQueryDTO queryDTO) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);

        if (StringUtils.hasText(queryDTO.getRoomNumber())) {
            queryWrapper.like("room_number", queryDTO.getRoomNumber());
        }
        if (StringUtils.hasText(queryDTO.getRoomType())) {
            queryWrapper.eq("room_type", queryDTO.getRoomType());
        }
        if (queryDTO.getMinPrice() != null) {
            queryWrapper.ge("price", queryDTO.getMinPrice());
        }
        if (queryDTO.getMaxPrice() != null) {
            queryWrapper.le("price", queryDTO.getMaxPrice());
        }
        if (queryDTO.getCapacity() != null) {
            queryWrapper.ge("capacity", queryDTO.getCapacity());
        }
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq("status", queryDTO.getStatus());
        }
        if (queryDTO.getMerchantId() != null) {
            queryWrapper.eq("merchant_id", queryDTO.getMerchantId());
        }
        if (StringUtils.hasText(queryDTO.getAddress())) {
            queryWrapper.like("address", queryDTO.getAddress());
        }

        return queryWrapper;
    }
}