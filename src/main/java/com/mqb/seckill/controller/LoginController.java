package com.mqb.seckill.controller;

import com.mqb.seckill.entity.LoginVo;
import com.mqb.seckill.redis.RedisService;
import com.mqb.seckill.result.Result;
import com.mqb.seckill.service.MiaoshaUserService;
import com.mqb.seckill.service.SampleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource
    private SampleService sampleService;
    @Resource
    private RedisService redisService;
    @Resource
    private MiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, LoginVo loginVo) {
        userService.login(response, loginVo);
        return Result.success(true);
    }

}
