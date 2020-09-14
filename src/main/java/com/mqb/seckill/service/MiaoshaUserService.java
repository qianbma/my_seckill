package com.mqb.seckill.service;

import com.mqb.seckill.entity.LoginVo;
import com.mqb.seckill.entity.MiaoshaUser;

import javax.servlet.http.HttpServletResponse;

public interface MiaoshaUserService {
    String COOKIE_NAME_TOKEN = "token";

    MiaoshaUser getById(String id);

    Boolean login(HttpServletResponse response, LoginVo loginVo);

    MiaoshaUser getByToken(HttpServletResponse response, String token);

    String getCookieTokenName();
}
