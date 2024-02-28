package com.example.geocoding.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RedisRequestCounter {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public Long getValue(Object key) {
        return (Long) redisTemplate.opsForValue().get(key);
    }

    public Boolean hasKey(Object key) {
        return redisTemplate.hasKey(key);
    }

    public void setData(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void incrementValue(Object key) {
        redisTemplate.opsForValue().increment(key);
    }
}
