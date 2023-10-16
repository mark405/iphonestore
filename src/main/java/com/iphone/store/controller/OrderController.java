package com.iphone.store.controller;

import com.iphone.store.dto.OrderDto;
import com.iphone.store.dto.ResponseDto;
import com.iphone.store.entity.Order;
import com.iphone.store.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/api/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class OrderController {
    private IOrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createOrder(@RequestBody OrderDto orderDto) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Order order = orderService.createOrder(orderDto);
        scheduledExecutorService.schedule(() -> orderService.checkOrderStatus(order.getOrderId()), 10, TimeUnit.MINUTES);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Order was successfully created"));
    }

    @PostMapping("/pay/{order_id}")
    public ResponseEntity<ResponseDto> payOrder(@PathVariable("order_id") Long orderId) {
        orderService.pay(orderId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Order was successfully paid"));
    }

}
