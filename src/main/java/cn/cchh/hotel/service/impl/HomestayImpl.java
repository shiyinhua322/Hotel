package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.HomestayDTO;
import cn.cchh.hotel.entity.Homestay;
import cn.cchh.hotel.mapper.HomestayMapper;
import cn.cchh.hotel.service.HomestayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 民宿服务实现类
 */
@Service
public class HomestayImpl extends ServiceImpl<HomestayMapper, Homestay> implements HomestayService {

    /**
     * 创建民宿
     *
     * @param homestayDTO 民宿信息
     * @return 是否创建成功
     */
    @Override
    public boolean createHomestay(HomestayDTO homestayDTO) {
        QueryWrapper<Homestay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", homestayDTO.getName());
        queryWrapper.eq("deleted", 0);
        Homestay existHomestay = this.getOne(queryWrapper);
        if (existHomestay != null) {
            throw new RuntimeException("该民宿名称已存在");
        }

        Homestay homestay = new Homestay();
        homestay.setMerchantId(homestayDTO.getMerchantId());
        homestay.setName(homestayDTO.getName());
        homestay.setAddress(homestayDTO.getAddress());
        homestay.setContactPhone(homestayDTO.getContactPhone());
        homestay.setDescription(homestayDTO.getDescription());
        homestay.setCoverImage(homestayDTO.getCoverImage());
        homestay.setStatus(homestayDTO.getStatus() != null ? homestayDTO.getStatus() : 1);
        homestay.setDeleted(0);
        return this.save(homestay);
    }

    /**
     * 更新民宿
     *
     * @param homestayDTO 民宿信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateHomestay(HomestayDTO homestayDTO) {
        if (homestayDTO.getId() == null) {
            throw new RuntimeException("民宿ID不能为空");
        }

        Homestay existHomestay = this.getById(homestayDTO.getId());
        if (existHomestay == null || existHomestay.getDeleted() == 1) {
            throw new RuntimeException("民宿不存在");
        }

        Homestay homestay = new Homestay();
        homestay.setId(homestayDTO.getId());
        homestay.setMerchantId(homestayDTO.getMerchantId());
        homestay.setName(homestayDTO.getName());
        homestay.setAddress(homestayDTO.getAddress());
        homestay.setContactPhone(homestayDTO.getContactPhone());
        homestay.setDescription(homestayDTO.getDescription());
        homestay.setCoverImage(homestayDTO.getCoverImage());
        if (homestayDTO.getStatus() != null) {
            homestay.setStatus(homestayDTO.getStatus());
        }

        return this.updateById(homestay);
    }

    /**
     * 删除民宿（逻辑删除）
     *
     * @param id 民宿ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteHomestay(Long id) {
        Homestay homestay = this.getById(id);
        if (homestay == null || homestay.getDeleted() == 1) {
            throw new RuntimeException("民宿不存在");
        }
        
        UpdateWrapper<Homestay> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("deleted", 1);
        return this.update(updateWrapper);
    }

    /**
     * 分页查询民宿
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 民宿分页结果
     */
    @Override
    public IPage<Homestay> queryHomestays(Integer pageNum, Integer pageSize) {
        Page<Homestay> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Homestay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.page(page, queryWrapper);
    }

}
