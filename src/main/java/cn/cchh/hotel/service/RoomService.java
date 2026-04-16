
package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.RoomDTO;
import cn.cchh.hotel.dto.RoomQueryDTO;
import cn.cchh.hotel.entity.Room;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoomService extends IService<Room> {

    /**
     * 添加房间
     * @param roomDTO 房间信息
     * @return 是否成功
     */
    boolean addRoom(RoomDTO roomDTO);

    /**
     * 更新房间信息
     * @param roomDTO 房间信息
     * @return 是否成功
     */
    boolean updateRoom(RoomDTO roomDTO);

    /**
     * 删除房间（逻辑删除）
     * @param id 房间ID
     * @return 是否成功
     */
    boolean deleteRoom(Long id);

    /**
     * 根据ID获取房间信息
     * @param id 房间ID
     * @return 房间信息
     */
    Room getRoomById(Long id);

    /**
     * 分页查询房间
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    Page<Room> getRoomPage(RoomQueryDTO queryDTO);

    /**
     * 根据商家ID获取房间列表
     * @param merchantId 商家ID
     * @return 房间列表
     */
    List<Room> getRoomsByMerchantId(Long merchantId);

    /**
     * 根据状态获取房间列表
     * @param status 状态
     * @return 房间列表
     */
    List<Room> getRoomsByStatus(Integer status);

    /**
     * 更新房间状态
     * @param id 房间ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateRoomStatus(Long id, Integer status);

    /**
     * 搜索房间（支持多条件）
     * @param queryDTO 查询条件
     * @return 房间列表
     */
    List<Room> searchRooms(RoomQueryDTO queryDTO);
}