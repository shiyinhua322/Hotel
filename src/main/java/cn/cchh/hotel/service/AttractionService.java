package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.AttractionDTO;
import cn.cchh.hotel.entity.Attraction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 景点业务逻辑层接口
 * 提供景点管理的核心业务方法
 *
 * @author Hotel System
 * @since 2026-04-17
 */
public interface AttractionService extends IService<Attraction> {

    /**
     * 添加景点
     *
     * @param attractionDTO 景点信息
     * @return 是否添加成功
     */
    boolean addAttraction(AttractionDTO attractionDTO);

    /**
     * 更新景点信息
     *
     * @param attractionDTO 景点信息（必须包含景点ID）
     * @return 是否更新成功
     */
    boolean updateAttraction(AttractionDTO attractionDTO);

    /**
     * 删除景点（逻辑删除）
     *
     * @param id 景点ID
     * @return 是否删除成功
     */
    boolean deleteAttraction(Long id);

    /**
     * 根据ID获取景点详细信息
     *
     * @param id 景点ID
     * @return 景点信息，如果不存在或已删除则返回 null
     */
    Attraction getAttractionById(Long id);

    /**
     * 分页查询景点
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param name 景点名称（模糊查询）
     * @param isHot 是否热门
     * @param status 状态
     * @return 分页结果
     */
    Page<Attraction> getAttractionPage(Integer current, Integer size, String name, Integer isHot, Integer status);

    /**
     * 获取热门景点列表（按浏览量和排序值降序）
     *
     * @param limit 限制数量
     * @return 热门景点列表
     */
    List<Attraction> getHotAttractions(Integer limit);

    /**
     * 搜索景点（支持多条件组合查询）
     *
     * @param name 景点名称（模糊查询）
     * @param isHot 是否热门
     * @param status 状态
     * @return 符合条件的景点列表
     */
    List<Attraction> searchAttractions(String name, Integer isHot, Integer status);

    /**
     * 增加景点浏览量
     *
     * @param id 景点ID
     * @return 是否更新成功
     */
    boolean incrementViewCount(Long id);
}
