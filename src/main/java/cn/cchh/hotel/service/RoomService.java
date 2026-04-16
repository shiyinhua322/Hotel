
package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.RoomDTO;
import cn.cchh.hotel.dto.RoomQueryDTO;
import cn.cchh.hotel.entity.Room;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 房间业务逻辑层接口
 * 提供房间管理的核心业务方法
 * 
 * @author Hotel System
 * @since 2026-04-16
 */
public interface RoomService extends IService<Room> {

    /**
     * 添加房间
     * 
     * @param roomDTO 房间信息（包含房间号、类型、价格等）
     * @return 是否添加成功
     * @throws RuntimeException 如果房间号已存在则抛出异常
     */
    boolean addRoom(RoomDTO roomDTO);

    /**
     * 更新房间信息
     * 
     * @param roomDTO 房间信息（必须包含房间ID）
     * @return 是否更新成功
     * @throws RuntimeException 如果房间ID为空或房间不存在则抛出异常
     */
    boolean updateRoom(RoomDTO roomDTO);

    /**
     * 删除房间（逻辑删除，不真正从数据库删除）
     * 
     * @param id 房间ID
     * @return 是否删除成功
     */
    boolean deleteRoom(Long id);
    /**
     * 分页查询房间（支持多条件筛选）
     * 
     * @param queryDTO 查询条件（包含房间号、类型、价格区间、分页参数等）
     * @return 分页结果（包含房间列表和分页信息）
     */
    Page<Room> getRoomPage(RoomQueryDTO queryDTO);

    /**
     * 根据商家ID获取该商家的所有房间列表
     * 
     * @param merchantId 商家ID
     * @return 房间列表（按创建时间倒序）
     */
    List<Room> getRoomsByMerchantId(Long merchantId);

    /**
     * 根据状态获取房间列表
     * 
     * @param status 房间状态（0-不可用，1-可用，2-已预订，3-维修中）
     * @return 房间列表（按创建时间倒序）
     */
    List<Room> getRoomsByStatus(Integer status);

    /**
     * 更新房间状态
     * 
     * @param id 房间ID
     * @param status 新状态（0-不可用，1-可用，2-已预订，3-维修中）
     * @return 是否更新成功
     */
    boolean updateRoomStatus(Long id, Integer status);

    /**
     * 搜索房间（支持多条件组合查询）
     * 
     * @param queryDTO 查询条件（可包含房间号、类型、价格区间、地址等）
     * @return 符合条件的房间列表（按创建时间倒序）
     */
    List<Room> searchRooms(RoomQueryDTO queryDTO);
}