package com.orderservice.orderservice.controller;


import com.orderservice.orderservice.dto.OrderRequest;
import com.orderservice.orderservice.service.OrderService;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @TimeLimiter(name = "inventory")
//    @Retry(name = "inventory")
    public String placeOrder(@RequestBody OrderRequest orderRequest){

       return orderService.placeOrder(orderRequest);
    }

//    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
//        String message = "Ooops! Something went wrong,please order after some time";
//
//        return CompletableFuture.supplyAsync(()->message);
//    }
}
