package com.mqb.seckill.service.impl;

import com.mqb.seckill.entity.LoginVo;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.mapper.MiaoshaUserMapper;
import com.mqb.seckill.result.CodeMsg;
import com.mqb.seckill.service.MiaoShaUserService;
import com.mqb.seckill.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MiaoShaUserServiceImpl implements MiaoShaUserService {
    @Resource
    private MiaoshaUserMapper miaoshaUserMapper;

    @Override
    public MiaoshaUser getById(String id) {
        return miaoshaUserMapper.getById(id);
    }

    @Override
    public CodeMsg login(LoginVo loginVo) {
        if(loginVo==null){
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String frontPass = loginVo.getPassword();
        MiaoshaUser user = getById(mobile);
        if(user==null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calaPass = MD5Util.formPassToDBPass(frontPass, salt);
        if(!StringUtils.equals(calaPass,dbPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
