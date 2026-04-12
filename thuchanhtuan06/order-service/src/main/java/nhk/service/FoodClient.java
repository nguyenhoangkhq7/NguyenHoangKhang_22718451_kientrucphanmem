package nhk.service;

import nhk.dto.FoodDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "food-service", url = "http://localhost:8082/foods")
public interface FoodClient {
    @GetMapping("/{id}")
    FoodDto getFoodById(@PathVariable("id") Long id);
}
