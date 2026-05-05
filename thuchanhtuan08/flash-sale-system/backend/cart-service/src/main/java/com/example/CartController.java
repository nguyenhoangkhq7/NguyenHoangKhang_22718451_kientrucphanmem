package com.example;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class CartController {

    private final StringRedisTemplate redis;

    public CartController(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @PostMapping("/cart/add")
    public List<Long> add(@RequestBody CartRequest request) {
        String key = "cart:user_" + request.userId();
        String current = redis.opsForValue().get(key);
        String value = current == null || current.isBlank() ? "[{\"productId\":" + request.productId() + "}]" : current.substring(0, current.length() - 1) + ",{\"productId\":" + request.productId() + "}]";
        redis.opsForValue().set(key, value);
        return get(request.userId());
    }

    @GetMapping("/cart")
    public List<Long> get(@RequestParam Long userId) {
        String value = redis.opsForValue().get("cart:user_" + userId);
        if (value == null || value.isBlank()) {
            return List.of();
        }
        List<Long> ids = new ArrayList<>();
        for (String item : value.split("\\{\\\"productId\\\":")) {
            if (item.contains("}")) {
                ids.add(Long.valueOf(item.substring(0, item.indexOf('}'))));
            }
        }
        return ids;
    }
}