package com.project.coin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisConfig {

    @Autowired
    RedisTemplate redisTemplate;

   public Object get(String key){
    redisTemplate.opsForValue().get(key);

   }
}
