package com.mqb.seckill.service.impl;

import com.mqb.seckill.entity.GoodsVo;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.entity.OrderInfo;
import com.mqb.seckill.mapper.MiaoshaMapper;
import com.mqb.seckill.service.GoodsService;
import com.mqb.seckill.service.MiaoshaService;
import com.mqb.seckill.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MiaoshaServiceImpl implements MiaoshaService {
    @Resource
    private MiaoshaMapper miaoshaMapper;
    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;

    @Transactional
    @Override
    public OrderInfo miaosha(GoodsVo goods, MiaoshaUser miaoshaUser) {
        // 减库存
        goodsService.reduceStock(goods);
        // 下订单 写入秒杀订单
        return orderService.createOrder(goods, miaoshaUser);
    }
}
