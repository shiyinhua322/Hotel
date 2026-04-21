package cn.cchh.hotel.service.impl;
1
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.cchh.hotel.entity.ConsumableType;
import cn.cchh.hotel.mapper.ConsumableTypeMapper;
import cn.cchh.hotel.service.ConsumableTypeService;
import org.springframework.stereotype.Service;

@Service
public class ConsumableTypeServiceImpl extends ServiceImpl<ConsumableTypeMapper, ConsumableType> implements ConsumableTypeService {
}