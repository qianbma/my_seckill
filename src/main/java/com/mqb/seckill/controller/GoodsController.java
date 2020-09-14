package com.mqb.seckill.controller;

import com.mqb.seckill.constants.MiaoshaUserConstants;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private MiaoshaUserService miaoShaUserService;


    @RequestMapping("/to_list")
    public String toList(Model model,
                         HttpServletResponse response,
                         @CookieValue(value = MiaoshaUserConstants.COOKIE_NAME_TOKEN,required = false)String cookieToken,
                         @RequestParam(value = MiaoshaUserConstants.COOKIE_NAME_TOKEN,required = false)String paramToken){
        if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            return "login";
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        MiaoshaUser miaoshaUser = miaoShaUserService.getByToken(response, token);
        model.addAttribute("user",miaoshaUser);
        return "goods_list";
    }
}
