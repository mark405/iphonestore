package com.iphone.store.service;

import com.iphone.store.dto.GoodsDto;
import com.iphone.store.entity.Goods;

import java.util.List;

public interface IGoodsService {
    List<GoodsDto> findAll();
    List<Goods> create(List<GoodsDto> goodsDto);
}
