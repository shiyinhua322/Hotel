package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.HomestayDTO;
import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.entity.Homestay;
import cn.cchh.hotel.service.HomestayService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 民宿控制器
 */
@RestController
@RequestMapping("/homestay")
public class HomestayController {
//1
    @Autowired
    private HomestayService homestayService;

    /**
     * 创建民宿
     */
    @PostMapping("/create")
    public Result createHomestay(@Validated @RequestBody HomestayDTO homestayDTO) {
        try {
            boolean result = homestayService.createHomestay(homestayDTO);
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
     * 更新民宿
     */
    @PostMapping("/update")
    public Result updateHomestay(@Validated @RequestBody HomestayDTO homestayDTO) {
        try {
            boolean result = homestayService.updateHomestay(homestayDTO);
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
     * 删除民宿
     */
    @GetMapping("/delete/{id}")
    public Result deleteHomestay(@PathVariable Long id) {
        try {
            boolean result = homestayService.deleteHomestay(id);
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
     * 分页查询民宿
     */
    @GetMapping("/query")
    public Result queryHomestays(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            IPage<Homestay> page = homestayService.queryHomestays(pageNum, pageSize);
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
