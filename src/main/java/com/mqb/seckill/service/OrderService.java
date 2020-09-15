package com.mqb.seckill.service;

import com.mqb.seckill.entity.GoodsVo;
import com.mqb.seckill.entity.MiaoshaOrder;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.entity.OrderInfo;

public interface OrderService {
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long id, long goodsId);

    OrderInfo createOrder(GoodsVo goods, MiaoshaUser miaoshaUser);
}
