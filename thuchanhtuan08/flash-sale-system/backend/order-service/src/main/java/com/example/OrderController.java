package com.example;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin("*")
@RestController
public class OrderController {

    private static final Pattern PRODUCT_ID = Pattern.compile("\\\"productId\\\":(\\d+)");

    private final StringRedisTemplate redis;
    private final RestTemplate restTemplate;

    public OrderController(StringRedisTemplate redis, RestTemplate restTemplate) {
        this.redis = redis;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam Long userId) throws Exception {
        String key = "cart:user_" + userId;
        String cart = redis.opsForValue().get(key);
        if (cart == null || cart.isBlank()) {
            return "Thành công";
        }
        for (Long productId : readProductIds(cart)) {
            Boolean ok = restTemplate.postForObject("http://localhost:8084/stock/deduct?productId=" + productId, null, Boolean.class);
            if (Boolean.FALSE.equals(ok)) {
                return "Hết hàng";
            }
        }
        redis.delete(key);
        return "Thành công";
    }

    private List<Long> readProductIds(String cart) {
        Matcher matcher = PRODUCT_ID.matcher(cart);
        List<Long> ids = new java.util.ArrayList<>();
        while (matcher.find()) {
            ids.add(Long.valueOf(matcher.group(1)));
        }
        return ids;
    }
}