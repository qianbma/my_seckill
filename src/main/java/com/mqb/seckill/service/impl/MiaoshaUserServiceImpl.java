package com.mqb.seckill.service.impl;

import com.mqb.seckill.entity.LoginVo;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.exception.GlobalException;
import com.mqb.seckill.mapper.MiaoshaUserMapper;
import com.mqb.seckill.redis.MiaoshaUserKey;
import com.mqb.seckill.redis.RedisService;
import com.mqb.seckill.result.CodeMsg;
import com.mqb.seckill.service.MiaoshaUserService;
import com.mqb.seckill.util.MD5Util;
import com.mqb.seckill.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService {
    @Resource
    private MiaoshaUserMapper miaoshaUserMapper;
    @Resource
    private RedisService redisService;

    @Override
    public MiaoshaUser getById(String id) {
        return miaoshaUserMapper.getById(id);
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if (miaoshaUser != null) {
            addCookie(response, miaoshaUser);
        }
        return miaoshaUser;
    }

    @Override
    public Boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String frontPass = loginVo.getPassword();
        MiaoshaUser user = getById(mobile);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calaPass = MD5Util.formPassToDBPass(frontPass, salt);
        if (!StringUtils.equals(calaPass, dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //设置缓存和cookie
        addCookie(response, user);
        return true;
    }

    public String getCookieTokenName() {
        return COOKIE_NAME_TOKEN;
    }

    public void addCookie(HttpServletResponse response, MiaoshaUser user) {
        // 生成token
        String token = UUIDUtil.uuid();
        // 保存到redis
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
