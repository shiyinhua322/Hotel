package cn.cchh.hotel.mapper;

import cn.cchh.hotel.entity.Attraction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 景点信息 Mapper 接口
 *
 * @author Hotel System
 * @since 2026-04-17
 */
@Mapper
public interface AttractionMapper extends BaseMapper<Attraction> {
}
