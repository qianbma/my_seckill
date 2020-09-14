package com.mqb.seckill.mapper;

import com.mqb.seckill.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MiaoshaUserMapper {

    @Select("select * from sk_user where id = #{id}")
    MiaoshaUser getById(@Param("id") String id);
}
