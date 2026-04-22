package cn.cchh.hotel.service;
//1
import cn.cchh.hotel.dto.HomestayDTO;
import cn.cchh.hotel.entity.Homestay;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 民宿服务接口
 */
public interface HomestayService extends IService<Homestay> {

    /**
     * 创建民宿
     */
    boolean createHomestay(HomestayDTO homestayDTO);

    /**
     * 更新民宿
     */
    boolean updateHomestay(HomestayDTO homestayDTO);

    /**
     * 删除民宿
     */
    boolean deleteHomestay(Long id);

    /**
     * 分页查询民宿
     */
    IPage<Homestay> queryHomestays(Integer pageNum, Integer pageSize);
}
