package com.example.restaurant_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "userServiceClient", url = "http://localhost:8081", decode404 = true)
public interface UserServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}",
            consumes = "application/json", produces = "application/json")
    ResponseEntity<Object> getUser(@PathVariable Long userId);
}
