package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventorySeed implements CommandLineRunner {

    private final StringRedisTemplate redis;

    public InventorySeed(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @Override
    public void run(String... args) {
        redis.opsForValue().set("stock:1", "10");
        redis.opsForValue().set("stock:2", "10");
    }
}