package com.iphone.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDto {
    @JsonProperty("order_code")
    private String orderCode;

    private String title;

    private Double price;

    private Integer stock;
}
