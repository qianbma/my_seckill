package com.mqb.seckill.service.impl;

import com.mqb.seckill.entity.Goods;
import com.mqb.seckill.entity.GoodsVo;
import com.mqb.seckill.entity.MiaoshaGoods;
import com.mqb.seckill.mapper.GoodsMapper;
import com.mqb.seckill.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long id) {
        return goodsMapper.getGoodsVoByGoodsId(id);
    }

    @Override
    @Transactional
    public void reduceStock(GoodsVo goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        // sql中减1
        g.setStockCount(goods.getStockCount());
        goodsMapper.reduceStock(g);
    }
}
