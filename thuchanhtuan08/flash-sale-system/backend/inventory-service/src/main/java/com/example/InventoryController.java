package com.example;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class InventoryController {

    private final StringRedisTemplate redis;

    public InventoryController(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @PostMapping("/stock/deduct")
    public boolean deduct(@RequestParam Long productId) {
        String key = "stock:" + productId;
        Long stock = redis.opsForValue().decrement(key);
        if (stock != null && stock < 0) {
            redis.opsForValue().increment(key);
            return false;
        }
        return true;
    }
}