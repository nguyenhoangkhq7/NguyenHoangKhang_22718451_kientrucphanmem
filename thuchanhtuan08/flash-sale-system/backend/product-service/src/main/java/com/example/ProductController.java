package com.example;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
public class ProductController {

    private final StringRedisTemplate redis;

    public ProductController(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @GetMapping("/products")
    public List<Product> all() {
        Set<String> keys = Optional.ofNullable(redis.keys("product:*"))
                .orElse(Set.of());
        return keys.stream().map(this::read).sorted(Comparator.comparing(Product::id)).toList();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> one(@PathVariable Long id) {
        Product product = read("product:" + id);
        return product == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    private Product read(String key) {
        String value = redis.opsForValue().get(key);
        if (value == null || value.isBlank()) {
            return null;
        }
        String[] parts = value.split("\\|", 3);
        return new Product(Long.valueOf(parts[0]), parts[1], Double.parseDouble(parts[2]));
    }
}