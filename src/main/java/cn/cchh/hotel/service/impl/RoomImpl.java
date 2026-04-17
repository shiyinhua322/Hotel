
package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.RoomDTO;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 房间业务逻辑层实现类
 * 实现房间管理的核心业务逻辑
 * 
 * @author Hotel System
 * @since 2026-04-16
 */
@Service
public class RoomImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    /**
     * 添加房间
     * 1. 检查房间号是否已存在
     * 2. 设置默认状态为可用（1）
     * 3. 设置逻辑删除标识为未删除（0）
     * 4. 自动填充创建时间和更新时间
     *
     * @param roomDTO 房间信息
     * @return 是否添加成功
     * @throws RuntimeException 如果房间号已存在则抛出异常
     */
    @Override
    public boolean addRoom(RoomDTO roomDTO) {
        // 检查房间号是否已存在（排除已删除的房间）
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_number", roomDTO.getRoomNumber());
        queryWrapper.eq("deleted", 0);
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("房间号已存在");
        }

        // 创建房间对象并复制属性
        Room room = new Room();
        BeanUtils.copyProperties(roomDTO, room);
        
        // 设置默认值
        room.setStatus(room.getStatus() != null ? room.getStatus() : 1); // 默认状态：可用
        room.setDeleted(0); // 未删除
        room.setCreateTime(LocalDateTime.now()); // 创建时间
        room.setUpdateTime(LocalDateTime.now()); // 更新时间

        return this.save(room);
    }

    /**
     * 更新房间信息
     * 1. 验证房间ID是否存在
     * 2. 检查房间是否已被删除
     * 3. 更新房间信息并刷新更新时间
     *
     * @param roomDTO 房间信息（必须包含ID）
     * @return 是否更新成功
     * @throws RuntimeException 如果房间ID为空或房间不存在则抛出异常
     */
    @Override
    public boolean updateRoom(RoomDTO roomDTO) {
        // 验证房间ID
        if (roomDTO.getId() == null) {
            throw new RuntimeException("房间ID不能为空");
        }

        // 检查房间是否存在且未被删除
        Room existRoom = this.getById(roomDTO.getId());
        if (existRoom == null || existRoom.getDeleted() == 1) {
            throw new RuntimeException("房间不存在");
        }

        // 创建更新对象
        Room room = new Room();
        BeanUtils.copyProperties(roomDTO, room);
        room.setUpdateTime(LocalDateTime.now()); // 更新更新时间

        // 构建更新条件
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", roomDTO.getId());
        updateWrapper.eq("deleted", 0);

        return this.update(room, updateWrapper);
    }

    /**
     * 删除房间（逻辑删除）
     * 将 deleted 字段设置为 1，不从数据库真正删除
     *
     * @param id 房间ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteRoom(Long id) {
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("deleted", 1); // 标记为已删除
        updateWrapper.set("update_time", LocalDateTime.now()); // 更新更新时间
        return this.update(updateWrapper);
    }

    /**
     * 根据ID获取房间信息
     * 只返回未删除的房间
     *
     * @param id 房间ID
     * @return 房间信息，不存在或已删除则返回 null
     */
    @Override
    public Room getRoomById(Long id) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("deleted", 0); // 只查询未删除的房间
        return this.getOne(queryWrapper);
    }

    /**
     * 分页查询房间
     * 支持多条件筛选，按创建时间倒序排列
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param roomNumber 房间号（模糊查询）
     * @param roomType 房间类型（精确匹配）
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param capacity 容纳人数
     * @param status 房间状态
     * @param merchantId 商家ID
     * @param address 地址（模糊查询）
     * @return 分页结果
     */
    @Override
    public Page<Room> getRoomPage(Integer current, Integer size, String roomNumber,
                                  String roomType, BigDecimal minPrice, BigDecimal maxPrice,
                                  Integer capacity, Integer status, Long merchantId, String address) {
        // 创建分页对象
        Page<Room> page = new Page<>(current != null ? current : 1, size != null ? size : 10);
        
        // 构建查询条件
        QueryWrapper<Room> queryWrapper = buildQueryWrapper(roomNumber, roomType, minPrice,
                                                            maxPrice, capacity, status,
                                                            merchantId, address);
        queryWrapper.orderByDesc("create_time"); // 按创建时间倒序
        
        return this.page(page, queryWrapper);
    }

    /**
     * 根据商家ID获取房间列表
     * 返回该商家所有未删除的房间，按创建时间倒序
     *
     * @param merchantId 商家ID
     * @return 房间列表
     */
    @Override
    public List<Room> getRoomsByMerchantId(Long merchantId) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id", merchantId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    /**
     * 根据状态获取房间列表
     * 返回指定状态的所有未删除房间，按创建时间倒序
     *
     * @param status 房间状态
     * @return 房间列表
     */
    @Override
    public List<Room> getRoomsByStatus(Integer status) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    /**
     * 更新房间状态
     * 可用于切换房间状态（如：可用→已预订，已预订→可用等）
     *
     * @param id 房间ID
     * @param status 新状态
     * @return 是否更新成功
     */
    @Override
    public boolean updateRoomStatus(Long id, Integer status) {
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.eq("deleted", 0); // 只能更新未删除的房间
        updateWrapper.set("status", status);
        updateWrapper.set("update_time", LocalDateTime.now());
        return this.update(updateWrapper);
    }

    /**
     * 搜索房间（多条件组合查询）
     * 支持房间号、类型、价格区间、容量、状态、商家、地址等多条件组合
     *
     * @param roomNumber 房间号（模糊查询）
     * @param roomType 房间类型（精确匹配）
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param capacity 容纳人数
     * @param status 房间状态
     * @param merchantId 商家ID
     * @param address 地址（模糊查询）
     * @return 符合条件的房间列表
     */
    @Override
    public List<Room> searchRooms(String roomNumber, String roomType, BigDecimal minPrice,
                                  BigDecimal maxPrice, Integer capacity, Integer status,
                                  Long merchantId, String address) {
        QueryWrapper<Room> queryWrapper = buildQueryWrapper(roomNumber, roomType, minPrice,
                                                            maxPrice, capacity, status,
                                                            merchantId, address);
        queryWrapper.orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    /**
     * 构建动态查询条件
     * 根据各个查询参数动态构建查询条件
     * 
     * @param roomNumber 房间号
     * @param roomType 房间类型
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param capacity 容纳人数
     * @param status 房间状态
     * @param merchantId 商家ID
     * @param address 地址
     * @return 查询包装器
     */
    private QueryWrapper<Room> buildQueryWrapper(String roomNumber, String roomType,
                                                 BigDecimal minPrice, BigDecimal maxPrice,
                                                 Integer capacity, Integer status,
                                                 Long merchantId, String address) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0); // 始终过滤已删除的房间

        // 房间号模糊查询
        if (StringUtils.hasText(roomNumber)) {
            queryWrapper.like("room_number", roomNumber);
        }
        
        // 房间类型精确匹配
        if (StringUtils.hasText(roomType)) {
            queryWrapper.eq("room_type", roomType);
        }
        
        // 价格区间查询
        if (minPrice != null) {
            queryWrapper.ge("price", minPrice); // 大于等于最低价
        }
        if (maxPrice != null) {
            queryWrapper.le("price", maxPrice); // 小于等于最高价
        }
        
        // 容纳人数查询（至少容纳多少人）
        if (capacity != null) {
            queryWrapper.ge("capacity", capacity);
        }
        
        // 状态精确匹配
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        
        // 商家ID精确匹配
        if (merchantId != null) {
            queryWrapper.eq("merchant_id", merchantId);
        }
        
        // 地址模糊查询
        if (StringUtils.hasText(address)) {
            queryWrapper.like("address", address);
        }

        return queryWrapper;
    }
}