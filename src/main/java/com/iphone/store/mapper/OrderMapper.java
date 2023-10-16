package com.iphone.store.mapper;

import com.iphone.store.dto.OrderDto;
import com.iphone.store.dto.OrderItemDto;
import com.iphone.store.entity.Customer;
import com.iphone.store.entity.Goods;
import com.iphone.store.entity.Order;
import com.iphone.store.entity.OrderItem;
import com.iphone.store.enums.OrderStatus;
import com.iphone.store.exception.NoCustomerFoundException;
import com.iphone.store.exception.NoIphoneModelFoundException;
import com.iphone.store.repository.CustomerRespository;
import com.iphone.store.repository.GoodsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class OrderMapper {
    private CustomerRespository customerRespository;
    private GoodsRepository goodsRepository;
    public OrderDto mapToOrderDto(Order order, OrderDto orderDto) {
        orderDto.setCustomerLogin(order.getCustomer().getLogin());

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItemList()) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setOrderCode(orderItem.getGoods().getOrderCode());
            orderItemDto.setAmount(orderItem.getAmount());
        }
        orderDto.setOrderItemDtoList(orderItemDtoList);

        return orderDto;
    }

    public Order mapToOrder(OrderDto orderDto, Order order) {
        String customerLogin = orderDto.getCustomerLogin();
        Optional<Customer> customerOptional = customerRespository.findByLogin(customerLogin);
        if (customerOptional.isEmpty()) {
            throw new NoCustomerFoundException("Customer with login: " + customerLogin + " wasn't found");
        }
        order.setCustomer(customerOptional.get());
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderDto.getOrderItemDtoList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setAmount(orderItem.getAmount());
            String orderCode = orderItemDto.getOrderCode();
            Optional<Goods> iphoneOptional = goodsRepository.findByOrderCode(orderCode);

            if (iphoneOptional.isEmpty()) {
                throw new NoIphoneModelFoundException("Iphone with order_code: " + orderCode + " wasn't found");
            }

            orderItem.setGoods(iphoneOptional.get());
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);
        if (order.getCustomer().getOrderList() == null) {
            List<Order> orderList = new ArrayList<>();
            order.getCustomer().setOrderList(orderList);
        }
        order.getCustomer().getOrderList().add(order);
        order.setStatus(OrderStatus.UNPAID);
        order.setCreatedAt(LocalDateTime.now());

        return order;
    }
}
