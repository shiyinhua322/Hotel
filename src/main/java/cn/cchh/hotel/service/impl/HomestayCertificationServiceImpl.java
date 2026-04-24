package cn.cchh.hotel.service.impl;

import cn.cchh.hotel.dto.HomestayCertificationDTO;
import cn.cchh.hotel.entity.HomestayCertification;
import cn.cchh.hotel.mapper.HomestayCertificationMapper;
import cn.cchh.hotel.service.HomestayCertificationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 民宿认证申请服务实现类
 */
@Service
public class HomestayCertificationServiceImpl extends ServiceImpl<HomestayCertificationMapper, HomestayCertification> implements HomestayCertificationService {

    /**
     * 创建民宿认证申请
     *
     * @param certificationDTO 认证申请信息
     * @return 是否创建成功
     */
    @Override
    public boolean createCertification(HomestayCertificationDTO certificationDTO) {
        QueryWrapper<HomestayCertification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id", certificationDTO.getMerchantId());
        queryWrapper.eq("homestay_name", certificationDTO.getHomestayName());
        queryWrapper.eq("deleted", 0);
        HomestayCertification existCertification = this.getOne(queryWrapper);
        if (existCertification != null) {
            throw new RuntimeException("该商家的民宿认证申请已存在");
        }

        HomestayCertification certification = new HomestayCertification();
        certification.setMerchantId(certificationDTO.getMerchantId());
        certification.setHomestayName(certificationDTO.getHomestayName());
        certification.setAddress(certificationDTO.getAddress());
        certification.setContactPhone(certificationDTO.getContactPhone());
        certification.setDescription(certificationDTO.getDescription());
        certification.setIdCardNumber(certificationDTO.getIdCardNumber());
        certification.setBusinessLicenseImage(certificationDTO.getBusinessLicenseImage());
        certification.setStatus("pending");
        certification.setDeleted(0);
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        certification.setSubmitTime(now.format(formatter));
        
        return this.save(certification);
    }

    /**
     * 更新民宿认证申请
     *
     * @param certificationDTO 认证申请信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateCertification(HomestayCertificationDTO certificationDTO) {
        if (certificationDTO.getId() == null) {
            throw new RuntimeException("认证ID不能为空");
        }

        HomestayCertification existCertification = this.getById(certificationDTO.getId());
        if (existCertification == null || existCertification.getDeleted() == 1) {
            throw new RuntimeException("认证申请不存在");
        }

        HomestayCertification certification = new HomestayCertification();
        certification.setId(certificationDTO.getId());
        certification.setMerchantId(certificationDTO.getMerchantId());
        certification.setHomestayName(certificationDTO.getHomestayName());
        certification.setAddress(certificationDTO.getAddress());
        certification.setContactPhone(certificationDTO.getContactPhone());
        certification.setDescription(certificationDTO.getDescription());
        certification.setIdCardNumber(certificationDTO.getIdCardNumber());
        certification.setBusinessLicenseImage(certificationDTO.getBusinessLicenseImage());
        
        if (certificationDTO.getStatus() != null) {
            certification.setStatus(certificationDTO.getStatus());
        }
        
        if (certificationDTO.getRejectReason() != null) {
            certification.setRejectReason(certificationDTO.getRejectReason());
        }
        
        if (certificationDTO.getReviewTime() != null) {
            certification.setReviewTime(certificationDTO.getReviewTime());
        }

        return this.updateById(certification);
    }

    /**
     * 删除民宿认证申请（逻辑删除）
     *
     * @param id 认证ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteCertification(Long id) {
        HomestayCertification certification = this.getById(id);
        if (certification == null || certification.getDeleted() == 1) {
            throw new RuntimeException("认证申请不存在");
        }
        
        UpdateWrapper<HomestayCertification> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("deleted", 1);
        return this.update(updateWrapper);
    }

    /**
     * 分页查询民宿认证申请
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 认证申请分页结果
     */
    @Override
    public IPage<HomestayCertification> queryCertifications(Integer pageNum, Integer pageSize) {
        Page<HomestayCertification> page = new Page<>(pageNum, pageSize);
        QueryWrapper<HomestayCertification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.page(page, queryWrapper);
    }
}
