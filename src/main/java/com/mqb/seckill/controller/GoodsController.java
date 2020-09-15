package com.mqb.seckill.controller;

import com.mqb.seckill.entity.GoodsVo;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.service.GoodsService;
import com.mqb.seckill.service.MiaoshaUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private MiaoshaUserService miaoShaUserService;
    @Resource
    private GoodsService goodsService;

    /**
     * 第一次测试：未优化前，并发10的情况下，处理10.7个请求。并发1000,吞吐55
     *
     * @param model
     * @param miaoshaUser inject from UserArgument Resolver in config package
     * @return
     */
    @RequestMapping("/to_list")
    public String toList(Model model, MiaoshaUser miaoshaUser) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail")
    public String toDetail(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId") long goodsId) {
        if (miaoshaUser == null) {
            return "login";
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {//秒杀未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 100);
        } else if (now < endAt) {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        } else { //秒杀已结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }
        model.addAttribute("user", miaoshaUser);
        model.addAttribute("goods", goods);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        return "goods_detail";
    }
}
