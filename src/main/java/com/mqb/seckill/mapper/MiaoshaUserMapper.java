package com.mqb.seckill.mapper;

import com.mqb.seckill.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MiaoshaUserMapper {

    @Select("select * from miaosha_user where id = #{id}")
    MiaoshaUser getById(@Param("id") String id);

    @Update("update miaosha_user set password= #{password} where id = #{id}")
    void update(MiaoshaUser toBeUpdate);
}
