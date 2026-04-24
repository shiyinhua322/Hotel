package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.HomestayCertificationDTO;
import cn.cchh.hotel.entity.HomestayCertification;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 民宿认证申请服务接口
 */
public interface HomestayCertificationService extends IService<HomestayCertification> {

    /**
     * 创建民宿认证申请
     */
    boolean createCertification(HomestayCertificationDTO certificationDTO);

    /**
     * 更新民宿认证申请
     */
    boolean updateCertification(HomestayCertificationDTO certificationDTO);

    /**
     * 删除民宿认证申请
     */
    boolean deleteCertification(Long id);

    /**
     * 分页查询民宿认证申请
     */
    IPage<HomestayCertification> queryCertifications(Integer pageNum, Integer pageSize);
}
