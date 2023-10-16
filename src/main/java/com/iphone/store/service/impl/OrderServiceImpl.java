package com.iphone.store.service.impl;

import com.iphone.store.dto.OrderDto;
import com.iphone.store.entity.Customer;
import com.iphone.store.entity.Order;
import com.iphone.store.entity.OrderItem;
import com.iphone.store.enums.OrderStatus;
import com.iphone.store.exception.NoCustomerFoundException;
import com.iphone.store.exception.NoOrderWithSuchIdException;
import com.iphone.store.mapper.OrderMapper;
import com.iphone.store.repository.CustomerRespository;
import com.iphone.store.repository.OrderRepository;
import com.iphone.store.service.IOrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    @Override
    public Order createOrder(OrderDto orderDto) {
        Order order = orderMapper.mapToOrder(orderDto, new Order());

        Order createdOrder = orderRepository.save(order);

        return createdOrder;
    }

    @Override
    @Transactional
    public void pay(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new NoOrderWithSuchIdException("No order with id: " + orderId);
        }

        optionalOrder.get().setStatus(OrderStatus.PAID);
    }

    @Override
    @Transactional
    public void checkOrderStatus(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new NoOrderWithSuchIdException("No order with id: " + orderId);
        }
        System.out.println("hello");
        if (optionalOrder.get().getStatus().equals(OrderStatus.UNPAID)){
            orderRepository.deleteById(orderId);
        }
    }
}
