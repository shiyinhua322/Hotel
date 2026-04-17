package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.RoomTypeDTO;
import cn.cchh.hotel.entity.RoomType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 房型服务接口
 */
public interface RoomTypeService extends IService<RoomType> {

    /**
     * 创建房型
     */
    boolean createRoomType(RoomTypeDTO roomTypeDTO);

    /**
     * 更新房型
     */
    boolean updateRoomType(RoomTypeDTO roomTypeDTO);

    /**
     * 删除房型
     */
    boolean deleteRoomType(Long id);

    /**
     * 分页查询房型
     */
    IPage<RoomType> queryRoomTypes(Integer pageNum, Integer pageSize);
}
