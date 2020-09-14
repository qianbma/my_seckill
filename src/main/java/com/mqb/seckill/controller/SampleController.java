package com.mqb.seckill.controller;

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
@RequestMapping("/demo")
public class SampleController {
    @Resource
    private SampleService sampleService;
    @Resource
    private RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "mqb");
        return "hello";
    }

    @RequestMapping("/user")
    @ResponseBody
    public Result<User> getUserById() {
        User user = sampleService.getUserById("18181818181");
        return Result.success(user);
    }

    @GetMapping("/redis")
    @ResponseBody
    public Result<User> getUserFromRedis() {
        //前提：手动在redis中设置
        User user = redisService.get(UserKey.getById, "18362739052", User.class);
        return Result.success(user);
    }

    @GetMapping("/redis/set")
    @ResponseBody
    public Result<User> setRedis() {
        //
        User user = new User();
        user.setId(1L);
        user.setNickname("mqb");
        user.setPassword("123456");
        redisService.set(UserKey.getById, user.getId().toString(), user);
        User user1 = redisService.get(UserKey.getById, "1", User.class);
        return Result.success(user1);
    }
}
