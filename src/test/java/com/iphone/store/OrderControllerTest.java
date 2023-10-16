package com.iphone.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iphone.store.dto.OrderDto;
import com.iphone.store.dto.OrderItemDto;
import com.iphone.store.entity.Order;
import com.iphone.store.entity.OrderItem;
import com.iphone.store.enums.OrderStatus;
import com.iphone.store.repository.CustomerRespository;
import com.iphone.store.repository.GoodsRepository;
import com.iphone.store.repository.OrderRepository;
import com.iphone.store.service.IOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = IphoneStoreApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private CustomerRespository customerRespository;
    @Autowired
    private OrderRepository orderRepository;
    @Test
    public void whenCreateOrder_thenStatus201() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerLogin("mark");
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderCode("iphone10");
        orderItemDto.setAmount(15);
        orderDto.setOrderItemDtoList(List.of(orderItemDto));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value("201"))
                .andExpect(jsonPath("$.statusMsg").value("Order was successfully created"));
    }

    @Test
    public void whenCreateOrderWithNonExistingUser_thenStatus400() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerLogin("markr");
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderCode("iphone10");
        orderItemDto.setAmount(15);
        orderDto.setOrderItemDtoList(List.of(orderItemDto));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenCreateOrderWithNonExistingIphone_thenStatus400() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerLogin("markr");
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderCode("iphone10000");
        orderItemDto.setAmount(15);
        orderDto.setOrderItemDtoList(List.of(orderItemDto));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPayOrder_thenStatus201() throws Exception {
        Order order = new Order();

        order.setCustomer(customerRespository.findByLogin("mark").get());
        order.setStatus(OrderStatus.UNPAID);
        order.setCreatedAt(LocalDateTime.now());

        OrderItem orderItem = new OrderItem();
        orderItem.setGoods(goodsRepository.findByOrderCode("iphone10").get());
        orderItem.setAmount(15);
        orderItem.setOrder(order);
        order.setOrderItemList(List.of(orderItem));

        Order createdOrder = orderRepository.save(order);
        Long orderId = createdOrder.getOrderId();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/pay/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value("201"))
                .andExpect(jsonPath("$.statusMsg").value("Order was successfully paid"));
    }

    @Test
    public void whenPayNonExistingOrder_thenStatus400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/pay/" + 15)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenNoPayForOrderWithin10Mins_thenItIsDeleted() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerLogin("mark");
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderCode("iphone10");
        orderItemDto.setAmount(15);
        orderDto.setOrderItemDtoList(List.of(orderItemDto));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value("201"))
                .andExpect(jsonPath("$.statusMsg").value("Order was successfully created"));

        Thread.sleep(600000);

        assertEquals(orderRepository.findAll().size(), 0);

    }
}
