
package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.RoomTypeDTO;
import cn.cchh.hotel.dto.RoomTypeQueryDTO;
import cn.cchh.hotel.entity.RoomType;
import cn.cchh.hotel.mapper.RoomTypeMapper;
import cn.cchh.hotel.service.RoomTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeMapper, RoomType> implements RoomTypeService {

    @Override
    public boolean createRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();
        roomType.setHomestayId(roomTypeDTO.getHomestayId());
        roomType.setTypeName(roomTypeDTO.getTypeName());
        roomType.setIntro(roomTypeDTO.getIntro());
        roomType.setPrice(roomTypeDTO.getPrice());
        roomType.setTotalRooms(roomTypeDTO.getTotalRooms());
        roomType.setLeftRooms(roomTypeDTO.getLeftRooms() != null ? roomTypeDTO.getLeftRooms() : roomTypeDTO.getTotalRooms());
        roomType.setCoverUrl(roomTypeDTO.getCoverUrl());
        roomType.setDetail(roomTypeDTO.getDetail());
        roomType.setStatus(StringUtils.hasText(roomTypeDTO.getStatus()) ? roomTypeDTO.getStatus() : "上架");

        return this.save(roomType);
    }

    @Override
    public boolean updateRoomType(RoomTypeDTO roomTypeDTO) {
        if (roomTypeDTO.getId() == null) {
            throw new RuntimeException("房间类型ID不能为空");
        }

        RoomType existRoomType = this.getById(roomTypeDTO.getId());
        if (existRoomType == null) {
            throw new RuntimeException("房间类型不存在");
        }

        RoomType roomType = new RoomType();
        roomType.setId(roomTypeDTO.getId());
        roomType.setHomestayId(roomTypeDTO.getHomestayId());
        roomType.setTypeName(roomTypeDTO.getTypeName());
        roomType.setIntro(roomTypeDTO.getIntro());
        roomType.setPrice(roomTypeDTO.getPrice());
        roomType.setTotalRooms(roomTypeDTO.getTotalRooms());
        roomType.setLeftRooms(roomTypeDTO.getLeftRooms());
        roomType.setCoverUrl(roomTypeDTO.getCoverUrl());
        roomType.setDetail(roomTypeDTO.getDetail());
        roomType.setStatus(roomTypeDTO.getStatus());

        return this.updateById(roomType);
    }

    @Override
    public boolean deleteRoomType(Long id) {
        RoomType roomType = this.getById(id);
        if (roomType == null) {
            throw new RuntimeException("房间类型不存在");
        }
        return this.removeById(id);
    }

    @Override
    public RoomType getRoomTypeById(Long id) {
        RoomType roomType = this.getById(id);
        if (roomType == null) {
            throw new RuntimeException("房间类型不存在");
        }
        return roomType;
    }

    @Override
    public IPage<RoomType> queryRoomTypes(RoomTypeQueryDTO queryDTO) {
        Page<RoomType> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        QueryWrapper<RoomType> queryWrapper = new QueryWrapper<>();

        if (queryDTO.getHomestayId() != null) {
            queryWrapper.eq("homestay_id", queryDTO.getHomestayId());
        }

        if (StringUtils.hasText(queryDTO.getTypeName())) {
            queryWrapper.like("type_name", queryDTO.getTypeName());
        }

        if (StringUtils.hasText(queryDTO.getStatus())) {
            queryWrapper.eq("status", queryDTO.getStatus());
        }

        if (queryDTO.getMinPrice() != null) {
            queryWrapper.ge("price", queryDTO.getMinPrice());
        }

        if (queryDTO.getMaxPrice() != null) {
            queryWrapper.le("price", queryDTO.getMaxPrice());
        }

        queryWrapper.orderByDesc("create_time");

        return this.page(page, queryWrapper);
    }

    @Override
    public IPage<RoomType> getRoomTypesByHomestayId(Long homestayId, Integer pageNum, Integer pageSize) {
        Page<RoomType> page = new Page<>(pageNum, pageSize);
        QueryWrapper<RoomType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homestay_id", homestayId);
        queryWrapper.orderByDesc("create_time");

        return this.page(page, queryWrapper);
    }
}
