package com.mqb.seckill.controller;

import com.mqb.seckill.entity.GoodsVo;
import com.mqb.seckill.entity.MiaoshaOrder;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.entity.OrderInfo;
import com.mqb.seckill.result.CodeMsg;
import com.mqb.seckill.service.GoodsService;
import com.mqb.seckill.service.MiaoshaService;
import com.mqb.seckill.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;
    @Resource
    private MiaoshaService miaoshaService;

    @RequestMapping("do_miaosha")
    public String doMiaosha(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",miaoshaUser);
        if(miaoshaUser==null){
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stockCount = goods.getStockCount();
        if(stockCount<=0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER);
            return "miaosha_fail";
        }
        // 判断是否已经秒杀到了
        MiaoshaOrder order =  orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(),goodsId);
        if(order!=null){
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_REPEATE);
            return "miaosha_fail";
        }
        // 减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(goods,miaoshaUser);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";
    }

}
