package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.AttractionDTO;
import cn.cchh.hotel.entity.Attraction;
import cn.cchh.hotel.mapper.AttractionMapper;
import cn.cchh.hotel.service.AttractionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 景点业务逻辑层实现类
 * 实现景点管理的核心业务逻辑
 *
 * @author Hotel System
 * @since 2026-04-17
 */
@Service
public class AttractionImpl extends ServiceImpl<AttractionMapper, Attraction> implements AttractionService {

    /**
     * 添加景点
     * 设置默认值并保存到数据库
     *
     * @param attractionDTO 景点信息
     * @return 是否添加成功
     */
    @Override
    public boolean addAttraction(AttractionDTO attractionDTO) {
        Attraction attraction = new Attraction();
        BeanUtils.copyProperties(attractionDTO, attraction);

        // 设置默认值
        if (attraction.getRating() == null) {
            attraction.setRating(new java.math.BigDecimal("5.0"));
        }
        if (attraction.getViewCount() == null) {
            attraction.setViewCount(0);
        }
        if (attraction.getIsHot() == null) {
            attraction.setIsHot(0);
        }
        if (attraction.getStatus() == null) {
            attraction.setStatus(1);
        }
        if (attraction.getSortOrder() == null) {
            attraction.setSortOrder(0);
        }
        attraction.setDeleted(0);
        attraction.setCreateTime(LocalDateTime.now());
        attraction.setUpdateTime(LocalDateTime.now());

        return this.save(attraction);
    }

    /**
     * 更新景点信息
     * 验证景点ID是否存在，然后更新信息
     *
     * @param attractionDTO 景点信息（必须包含ID）
     * @return 是否更新成功
     */
    @Override
    public boolean updateAttraction(AttractionDTO attractionDTO) {
        if (attractionDTO.getId() == null) {
            throw new RuntimeException("景点ID不能为空");
        }

        Attraction existAttraction = this.getById(attractionDTO.getId());
        if (existAttraction == null || existAttraction.getDeleted() == 1) {
            throw new RuntimeException("景点不存在");
        }

        Attraction attraction = new Attraction();
        BeanUtils.copyProperties(attractionDTO, attraction);
        attraction.setUpdateTime(LocalDateTime.now());

        UpdateWrapper<Attraction> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", attractionDTO.getId());
        updateWrapper.eq("deleted", 0);

        return this.update(attraction, updateWrapper);
    }

    /**
     * 删除景点（逻辑删除）
     * 将 deleted 字段设置为 1
     *
     * @param id 景点ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAttraction(Long id) {
        UpdateWrapper<Attraction> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("deleted", 1);
        updateWrapper.set("update_time", LocalDateTime.now());
        return this.update(updateWrapper);
    }

    /**
     * 根据ID获取景点信息
     * 只返回未删除的景点，并增加浏览量
     *
     * @param id 景点ID
     * @return 景点信息，不存在或已删除则返回 null
     */
    @Override
    public Attraction getAttractionById(Long id) {
        QueryWrapper<Attraction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("deleted", 0);
        Attraction attraction = this.getOne(queryWrapper);

        // 增加浏览量
        if (attraction != null) {
            this.incrementViewCount(id);
        }

        return attraction;
    }

    /**
     * 分页查询景点
     * 支持多条件筛选，按排序值和创建时间倒序排列
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param name 景点名称（模糊查询）
     * @param isHot 是否热门
     * @param status 状态
     * @return 分页结果
     */
    @Override
    public Page<Attraction> getAttractionPage(Integer current, Integer size, String name, Integer isHot, Integer status) {
        Page<Attraction> page = new Page<>(current != null ? current : 1, size != null ? size : 10);

        QueryWrapper<Attraction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);

        if (StringUtils.hasText(name)) {
            queryWrapper.like("name", name.trim());
        }
        if (isHot != null) {
            queryWrapper.eq("is_hot", isHot);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        queryWrapper.orderByDesc("sort_order", "create_time");

        return this.page(page, queryWrapper);
    }

    /**
     * 获取热门景点列表
     * 按是否热门、浏览量和排序值降序排列
     *
     * @param limit 限制数量
     * @return 热门景点列表
     */
    @Override
    public List<Attraction> getHotAttractions(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }

        QueryWrapper<Attraction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        queryWrapper.eq("status", 1); // 只查询上架的景点
        queryWrapper.eq("is_hot", 1); // 只查询热门景点
        queryWrapper.orderByDesc("sort_order", "view_count", "create_time");
        queryWrapper.last("LIMIT " + limit);

        return this.list(queryWrapper);
    }

    /**
     * 搜索景点（多条件组合查询）
     *
     * @param name 景点名称（模糊查询）
     * @param isHot 是否热门
     * @param status 状态
     * @return 符合条件的景点列表
     */
    @Override
    public List<Attraction> searchAttractions(String name, Integer isHot, Integer status) {
        QueryWrapper<Attraction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);

        if (StringUtils.hasText(name)) {
            queryWrapper.like("name", name.trim());
        }
        if (isHot != null) {
            queryWrapper.eq("is_hot", isHot);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        queryWrapper.orderByDesc("sort_order", "view_count", "create_time");
        return this.list(queryWrapper);
    }

    /**
     * 增加景点浏览量
     *
     * @param id 景点ID
     * @return 是否更新成功
     */
    @Override
    public boolean incrementViewCount(Long id) {
        UpdateWrapper<Attraction> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.setSql("view_count = view_count + 1");
        return this.update(updateWrapper);
    }
}
