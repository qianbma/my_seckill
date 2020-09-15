package com.mqb.seckill.service.impl;

import com.mqb.seckill.entity.GoodsVo;
import com.mqb.seckill.entity.MiaoshaOrder;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.entity.OrderInfo;
import com.mqb.seckill.mapper.OrderMapper;
import com.mqb.seckill.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long id, long goodsId) {
        return orderMapper.getMiaoshaOrderByUserIdGoodsId(id, goodsId);
    }

    @Override
    @Transactional
    public OrderInfo createOrder(GoodsVo goods, MiaoshaUser miaoshaUser) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(1L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        // TO DO 使用枚举类型
        orderInfo.setStatus(0);//新建
        orderInfo.setUserId(miaoshaUser.getId());
        long orderId = orderMapper.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setUserId(miaoshaUser.getId());
        miaoshaOrder.setOrderId(orderId);
        orderMapper.insertMiaoshaOrder(miaoshaOrder);

        return orderInfo;
    }
}
