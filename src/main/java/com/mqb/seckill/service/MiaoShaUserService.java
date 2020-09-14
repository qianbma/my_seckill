package com.mqb.seckill.service;

import com.mqb.seckill.entity.LoginVo;
import com.mqb.seckill.entity.MiaoshaUser;
import com.mqb.seckill.result.CodeMsg;

public interface MiaoShaUserService {
    MiaoshaUser getById(String id);

    CodeMsg login(LoginVo loginVo);
}
