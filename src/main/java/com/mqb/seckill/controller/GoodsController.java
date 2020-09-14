package com.mqb.seckill.controller;

import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.service.MiaoshaUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private MiaoshaUserService miaoShaUserService;

    /**
     *
     * @param model
     * @param miaoshaUser  inject from UserArgument Resolver in config package
     * @return
     */
    @RequestMapping("/to_list")
    public String toList(Model model, MiaoshaUser miaoshaUser) {
        model.addAttribute("user", miaoshaUser);
        return "goods_list";
    }
}
