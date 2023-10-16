package com.iphone.store.mapper;

import com.iphone.store.dto.GoodsDto;
import com.iphone.store.entity.Goods;
import org.springframework.stereotype.Component;

@Component
public class GoodsMapper {
    public Goods mapToGoods(GoodsDto goodsDto, Goods goods) {
        goods.setOrderCode(goodsDto.getOrderCode());
        goods.setPrice(goodsDto.getPrice());
        goods.setTitle(goodsDto.getTitle());
        goods.setStock(goodsDto.getStock());

        return goods;
    }
}
