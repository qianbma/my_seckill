package com.mqb.seckill.controller;

import com.mqb.seckill.entity.LoginVo;
import com.mqb.seckill.entity.User;
import com.mqb.seckill.redis.RedisService;
import com.mqb.seckill.redis.UserKey;
import com.mqb.seckill.result.Result;
import com.mqb.seckill.service.SampleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource
    private SampleService sampleService;
    @Resource
    private RedisService redisService;

    @RequestMapping("/to_login")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo){
        String password = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        return null;
    }

}
