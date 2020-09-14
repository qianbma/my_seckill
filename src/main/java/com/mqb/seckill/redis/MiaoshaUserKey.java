package com.mqb.seckill.redis;

public class MiaoshaUserKey extends BasePrefix {

    // 一天
    public static final int TOKEN_EXPIRE = 1 * 24 * 2600;
    private String prefix;

    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
        this.prefix = prefix;
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");

    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");

    public MiaoshaUserKey withExpire(int seconds) {
        return new MiaoshaUserKey(seconds, prefix);
    }
}
