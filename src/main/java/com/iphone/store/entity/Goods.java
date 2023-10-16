package com.iphone.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "goods")
@Data
@NoArgsConstructor
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;
}
