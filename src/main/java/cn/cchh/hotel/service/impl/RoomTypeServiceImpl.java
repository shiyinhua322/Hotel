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
        queryWrapper.eq("deleted", 1);
        RoomType existRoomType = this.getOne(queryWrapper);
        if (existRoomType != null) {
            throw new RuntimeException("该房型名称已存在");
        }

        RoomType roomType = new RoomType();
        roomType.setTypeName(roomTypeDTO.getTypeName());
        roomType.setCapacity(roomTypeDTO.getCapacity());
        roomType.setDeleted(1);
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
        if (roomTypeDTO.getId() == null) {
            throw new RuntimeException("房型ID不能为空");
        }

        RoomType existRoomType = this.getById(roomTypeDTO.getId());
        if (existRoomType == null) {
            throw new RuntimeException("房型不存在");
        }

        RoomType roomType = new RoomType();
        roomType.setId(roomTypeDTO.getId());
        roomType.setTypeName(roomTypeDTO.getTypeName());
        roomType.setCapacity(roomTypeDTO.getCapacity());
        if (roomTypeDTO.getDeleted() != null) {
            roomType.setDeleted(roomTypeDTO.getDeleted());
        }

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
        
        // 业务删除，将deleted字段设置为0
        RoomType updateRoomType = new RoomType();
        updateRoomType.setId(id);
        updateRoomType.setDeleted(0);
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
        // 只查询未删除的数据
        queryWrapper.eq("deleted", 1);
        queryWrapper.orderByDesc("id");
        return this.page(page, queryWrapper);
    }

}
