package com.iphone.store.service;

import com.iphone.store.dto.OrderDto;
import com.iphone.store.entity.Order;

public interface IOrderService {
    Order createOrder(OrderDto orderDto);
    void pay(Long orderId);
    void checkOrderStatus(Long orderId);
}
