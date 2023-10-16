package com.iphone.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @JsonProperty("items")
    private List<OrderItemDto> orderItemDtoList;
    @JsonProperty("customer_login")
    private String customerLogin;
}
