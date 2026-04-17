package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.RoomTypeDTO;
import cn.cchh.hotel.entity.RoomType;
import cn.cchh.hotel.mapper.RoomTypeMapper;
import cn.cchh.hotel.service.RoomTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 房型服务实现类
 */
@Service
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeMapper, RoomType> implements RoomTypeService {

    /**
     * 创建房型
     *
     * @param roomTypeDTO 房型信息
     * @return 是否创建成功
     */
    @Override
    public boolean createRoomType(RoomTypeDTO roomTypeDTO) {
        // 检查是否已存在相同名称的房型
        QueryWrapper<RoomType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_name", roomTypeDTO.getTypeName());
        RoomType existRoomType = this.getOne(queryWrapper);
        if (existRoomType != null) {
            throw new RuntimeException("该房型名称已存在");
        }

        RoomType roomType = new RoomType();
        roomType.setTypeName(roomTypeDTO.getTypeName());
        roomType.setPrice(roomTypeDTO.getPrice());
        roomType.setTotalRooms(roomTypeDTO.getTotalRooms());
        roomType.setIntro(roomTypeDTO.getIntro());
        roomType.setCoverUrl(roomTypeDTO.getCoverUrl());
        roomType.setDetail(roomTypeDTO.getDetail());
        roomType.setStatus(roomTypeDTO.getStatus() != null ? roomTypeDTO.getStatus() : 0);
        roomType.setCreateTime(LocalDateTime.now());
        roomType.setUpdateTime(LocalDateTime.now());
        return this.save(roomType);
    }

    /**
     * 更新房型
     *
     * @param roomTypeDTO 房型信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateRoomType(RoomTypeDTO roomTypeDTO) {
        if (roomTypeDTO.getTypeId() == null) {
            throw new RuntimeException("房型ID不能为空");
        }

        RoomType existRoomType = this.getById(roomTypeDTO.getTypeId());
        if (existRoomType == null) {
            throw new RuntimeException("房型不存在");
        }

        RoomType roomType = new RoomType();
        roomType.setTypeId(roomTypeDTO.getTypeId());
        roomType.setTypeName(roomTypeDTO.getTypeName());
        roomType.setPrice(roomTypeDTO.getPrice());
        roomType.setTotalRooms(roomTypeDTO.getTotalRooms());
        roomType.setIntro(roomTypeDTO.getIntro());
        roomType.setCoverUrl(roomTypeDTO.getCoverUrl());
        roomType.setDetail(roomTypeDTO.getDetail());
        if (roomTypeDTO.getStatus() != null) {
            roomType.setStatus(roomTypeDTO.getStatus());
        }
        roomType.setUpdateTime(LocalDateTime.now());

        return this.updateById(roomType);
    }

    /**
     * 删除房型（业务删除）
     *
     * @param id 房型ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteRoomType(Long id) {
        RoomType roomType = this.getById(id);
        if (roomType == null) {
            throw new RuntimeException("房型不存在");
        }
        
        // 业务删除，将status字段设置为1（下架）
        RoomType updateRoomType = new RoomType();
        updateRoomType.setTypeId(id.intValue());
        updateRoomType.setStatus(1);
        updateRoomType.setUpdateTime(LocalDateTime.now());
        return this.updateById(updateRoomType);
    }

    /**
     * 分页查询房型
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 房型分页结果
     */
    @Override
    public IPage<RoomType> queryRoomTypes(Integer pageNum, Integer pageSize) {
        Page<RoomType> page = new Page<>(pageNum, pageSize);
        QueryWrapper<RoomType> queryWrapper = new QueryWrapper<>();
        // 只查询上架状态的数据（status=0）
        queryWrapper.eq("status", 0);
        queryWrapper.orderByDesc("type_id");
        return this.page(page, queryWrapper);
    }

}
