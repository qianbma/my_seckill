package com.mqb.seckill.controller;

import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.redis.RedisService;
import com.mqb.seckill.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private RedisService redisService;


    /**
     * test pressure:get userInfo from redis。10000并发(jmeter 配置10,0,1000)，380
     *
     * @param model
     * @param miaoshaUser
     * @return
     */
    @GetMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> getUserInfo(Model model, MiaoshaUser miaoshaUser) {
        return Result.success(miaoshaUser);
    }

}
