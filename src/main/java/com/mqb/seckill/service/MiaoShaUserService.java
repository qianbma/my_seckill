package com.mqb.seckill.service;

import com.mqb.seckill.entity.LoginVo;
import com.mqb.seckill.entity.MiaoshaUser;

public interface MiaoShaUserService {
    MiaoshaUser getById(String id);

    Boolean login(LoginVo loginVo);
}
