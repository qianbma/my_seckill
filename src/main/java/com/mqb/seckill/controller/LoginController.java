package com.mqb.seckill.controller;

import com.mqb.seckill.entity.LoginVo;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.entity.User;
import com.mqb.seckill.redis.RedisService;
import com.mqb.seckill.redis.UserKey;
import com.mqb.seckill.result.CodeMsg;
import com.mqb.seckill.result.Result;
import com.mqb.seckill.service.MiaoShaUserService;
import com.mqb.seckill.service.SampleService;
import com.mqb.seckill.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
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
    @Resource
    private MiaoShaUserService userService;

    @RequestMapping("/to_login")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo){
        String password = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(password)) {
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)) {
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        CodeMsg codeMsg = userService.login(loginVo);
        if(codeMsg.getCode()==0){
            return Result.success(true);
        }
        return Result.error(codeMsg);
    }

}
