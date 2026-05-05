package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StringRedisTemplate redis;

    public DataSeeder(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @Override
    public void run(String... args) {
        redis.opsForValue().set("product:1", "1|iPhone 15|29999");
        redis.opsForValue().set("product:2", "2|Samsung S24|24999");
    }
}