package com.mqb.seckill.mapper;

import com.mqb.seckill.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SampleMapper {
    @Select("select * from sk_user where id = #{id}")
    User getUserById(@Param("id") String id);
}
