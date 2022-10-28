package com.melson.files.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public  class RedisCacheUtil {
    @Autowired
    private static RedisTemplate<String,String> redisTemplate;

    //2.添加静态的变量
    public static RedisTemplate redis;

    @PostConstruct
    public static void getRedisTemplate(){
        redis=redisTemplate;
    }

    //1....其他的工具方法...
}

