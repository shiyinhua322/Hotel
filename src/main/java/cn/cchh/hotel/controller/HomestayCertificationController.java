package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.HomestayCertificationDTO;
import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.entity.HomestayCertification;
import cn.cchh.hotel.service.HomestayCertificationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 民宿认证申请控制器
 */
@RestController
@RequestMapping("/homestay-certification")
public class HomestayCertificationController {

    @Autowired
    private HomestayCertificationService certificationService;

    /**
     * 创建民宿认证申请
     */
    @PostMapping("/create")
    public Result createCertification(@Validated @RequestBody HomestayCertificationDTO certificationDTO) {
        try {
            boolean result = certificationService.createCertification(certificationDTO);
            if (result) {
                return Result.success("创建成功", null);
            } else {
                return Result.error("创建失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新民宿认证申请
     */
    @PostMapping("/update")
    public Result updateCertification(@Validated @RequestBody HomestayCertificationDTO certificationDTO) {
        try {
            boolean result = certificationService.updateCertification(certificationDTO);
            if (result) {
                return Result.success("更新成功", null);
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除民宿认证申请
     */
    @GetMapping("/delete/{id}")
    public Result deleteCertification(@PathVariable Long id) {
        try {
            boolean result = certificationService.deleteCertification(id);
            if (result) {
                return Result.success("删除成功", null);
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分页查询民宿认证申请
     */
    @GetMapping("/query")
    public Result queryCertifications(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            IPage<HomestayCertification> page = certificationService.queryCertifications(pageNum, pageSize);
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
