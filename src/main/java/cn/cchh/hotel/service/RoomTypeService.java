
package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.RoomTypeDTO;
import cn.cchh.hotel.dto.RoomTypeQueryDTO;
import cn.cchh.hotel.entity.RoomType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoomTypeService extends IService<RoomType> {

    /**
     * 创建房间类型
     */
    boolean createRoomType(RoomTypeDTO roomTypeDTO);

    /**
     * 更新房间类型
     */
    boolean updateRoomType(RoomTypeDTO roomTypeDTO);

    /**
     * 删除房间类型
     */
    boolean deleteRoomType(Long id);

    /**
     * 根据ID查询房间类型
     */
    RoomType getRoomTypeById(Long id);

    /**
     * 分页查询房间类型
     */
    IPage<RoomType> queryRoomTypes(RoomTypeQueryDTO queryDTO);

    /**
     * 根据民宿ID查询房间类型列表
     */
    IPage<RoomType> getRoomTypesByHomestayId(Long homestayId, Integer pageNum, Integer pageSize);
}
