package cn.cchh.hotel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.cchh.hotel.entity.ConsumableApplication;
import cn.cchh.hotel.mapper.ConsumableApplicationMapper;
import cn.cchh.hotel.service.ConsumableApplicationService;
import org.springframework.stereotype.Service;

@Service
public class ConsumableApplicationServiceImpl
        extends ServiceImpl<ConsumableApplicationMapper, ConsumableApplication>
        implements ConsumableApplicationService {
}