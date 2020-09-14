package com.mqb.seckill.service.impl;

import com.mqb.seckill.entity.User;
import com.mqb.seckill.mapper.SampleMapper;
import com.mqb.seckill.service.SampleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SampleServiceImpl implements SampleService {
    @Resource
    private SampleMapper sampleMapper;

    @Override
    public User getUserById(String id) {
        return sampleMapper.getUserById(id);
    }
}
