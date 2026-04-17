package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.AttractionDTO;
import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.entity.Attraction;
import cn.cchh.hotel.service.AttractionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 景点管理控制器
 * 提供景点管理的 RESTful API 接口
 *
 * @author Hotel System
 * @since 2026-04-17
 */
@RestController
@RequestMapping("/attraction")
public class AttractionController {

    @Autowired
    private AttractionService attractionService;

    /**
     * 添加景点
     * POST /attraction/add
     *
     * @param attractionDTO 景点信息（JSON格式）
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result addAttraction(@Validated(AttractionDTO.AddGroup.class) @RequestBody AttractionDTO attractionDTO) {
        try {
            boolean result = attractionService.addAttraction(attractionDTO);
            if (result) {
                return Result.success("景点添加成功");
            } else {
                return Result.error("景点添加失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新景点信息
     * POST /attraction/update
     *
     * @param attractionDTO 景点信息（必须包含景点ID）
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result updateAttraction(@Validated(AttractionDTO.UpdateGroup.class) @RequestBody AttractionDTO attractionDTO) {
        try {
            boolean result = attractionService.updateAttraction(attractionDTO);
            if (result) {
                return Result.success("景点更新成功");
            } else {
                return Result.error("景点更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除景点（逻辑删除）
     * DELETE /attraction/delete/{id}
     *
     * @param id 景点ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteAttraction(@PathVariable Long id) {
        try {
            boolean result = attractionService.deleteAttraction(id);
            if (result) {
                return Result.success("景点删除成功");
            } else {
                return Result.error("景点删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID获取景点详细信息
     * GET /attraction/{id}
     *
     * @param id 景点ID
     * @return 景点信息
     */
    @GetMapping("/{id}")
    public Result<Attraction> getAttractionById(@PathVariable Long id) {
        try {
            Attraction attraction = attractionService.getAttractionById(id);
            if (attraction != null) {
                return Result.success("查询成功", attraction);
            } else {
                return Result.error("景点不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分页查询景点
     * POST /attraction/page
     *
     * @param attractionDTO 查询参数（JSON格式）
     * @return 分页结果
     */
    @PostMapping("/page")
    public Result<Page<Attraction>> getAttractionPage(@RequestBody(required = false) AttractionDTO attractionDTO) {
        try {
            if (attractionDTO == null) {
                attractionDTO = new AttractionDTO();
            }
            Page<Attraction> page = attractionService.getAttractionPage(
                attractionDTO.getCurrent(),
                attractionDTO.getSize(),
                attractionDTO.getName(),
                attractionDTO.getIsHot(),
                attractionDTO.getStatus()
            );
            return Result.success("查询成功", page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取热门景点列表
     * GET /attraction/hot?limit=10
     *
     * @param limit 限制数量（默认10）
     * @return 热门景点列表
     */
    @GetMapping("/hot")
    public Result<List<Attraction>> getHotAttractions(@RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<Attraction> attractions = attractionService.getHotAttractions(limit);
            return Result.success("查询成功", attractions);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 搜索景点
     * POST /attraction/search
     *
     * @param attractionDTO 查询参数（JSON格式）
     * @return 景点列表
     */
    @PostMapping("/search")
    public Result<List<Attraction>> searchAttractions(@RequestBody(required = false) AttractionDTO attractionDTO) {
        try {
            if (attractionDTO == null) {
                attractionDTO = new AttractionDTO();
            }
            List<Attraction> attractions = attractionService.searchAttractions(
                attractionDTO.getName(),
                attractionDTO.getIsHot(),
                attractionDTO.getStatus()
            );
            return Result.success("查询成功", attractions);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取所有景点列表（不分页）
     * GET /attraction/list
     *
     * @return 景点列表
     */
    @GetMapping("/list")
    public Result<List<Attraction>> getAllAttractions() {
        try {
            List<Attraction> attractions = attractionService.searchAttractions(null, null, 1);
            return Result.success("查询成功", attractions);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
