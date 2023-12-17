package com.leons.orderservice.controller;

import com.leons.orderservice.dto.OrderRequest;
import com.leons.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placedOrder(@RequestBody OrderRequest orderRequest){
        try{
            orderService.placeOrder(orderRequest);
        }
        catch(IllegalArgumentException e){
            return e.getMessage();
        }

        return  "Order Placed Successfully";
    }
}
