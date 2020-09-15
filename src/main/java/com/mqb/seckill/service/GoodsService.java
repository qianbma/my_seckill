package com.mqb.seckill.service;

import com.mqb.seckill.entity.GoodsVo;

import java.util.List;

public interface GoodsService {
    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long id);

    void reduceStock(GoodsVo goods);
}
