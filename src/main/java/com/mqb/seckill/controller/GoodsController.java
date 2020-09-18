package com.mqb.seckill.controller;

import com.mqb.seckill.entity.GoodsVo;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.redis.GoodsKey;
import com.mqb.seckill.redis.RedisService;
import com.mqb.seckill.service.GoodsService;
import com.mqb.seckill.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private MiaoshaUserService miaoShaUserService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private RedisService redisService;
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 第一次测试：未优化前，并发10的情况下，处理10.7个请求。并发1000,吞吐55
     * 45
     *
     * @param model
     * @param miaoshaUser inject from UserArgument Resolver in config package
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser miaoshaUser) {
        // 取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user", miaoshaUser);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        // 缓存中不存在则手动渲染
        SpringWebContext springWebContext = new SpringWebContext(request,
                response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
        // 渲染后保存到缓存中,商品列表数据可能会变化，所以缓存时间不应过长，这里设置60s
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail", produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId") long goodsId) {
        // 取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
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

        SpringWebContext springWebContext = new SpringWebContext(request,
                response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);
        // 渲染后保存到缓存中,数据可能会变化，所以缓存时间不应过长，这里设置60s
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;
//        return "goods_detail";

    }
}
