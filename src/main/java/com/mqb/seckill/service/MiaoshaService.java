package com.mqb.seckill.service;

import com.mqb.seckill.entity.GoodsVo;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.entity.OrderInfo;

public interface MiaoshaService {
    OrderInfo miaosha(GoodsVo goods, MiaoshaUser miaoshaUser);

}
