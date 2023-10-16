package com.iphone.store.service.impl;

import com.iphone.store.dto.GoodsDto;
import com.iphone.store.entity.Goods;
import com.iphone.store.mapper.GoodsMapper;
import com.iphone.store.repository.GoodsRepository;
import com.iphone.store.service.IGoodsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GoodsServiceImpl implements IGoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsMapper goodsMapper;
    @Override
    public List<GoodsDto> findAll() {
        List<Goods> goods = goodsRepository.findAll();
        List<GoodsDto> goodsDtos = new ArrayList<>();
        for (Goods good : goods) {
            GoodsDto goodsDto = new GoodsDto();
            goodsDto.setOrderCode(good.getOrderCode());
            goodsDto.setStock(good.getStock());
            goodsDto.setPrice(good.getPrice());
            goodsDto.setTitle(good.getTitle());
            goodsDtos.add(goodsDto);
        }
        return goodsDtos;
    }

    @Override
    @Transactional
    public List<Goods> create(List<GoodsDto> goodsDto) {

        List<Goods> goodsList = new ArrayList<>();
        for (GoodsDto dto : goodsDto) {
            Goods goods = goodsMapper.mapToGoods(dto, new Goods());
            goodsList.add(goods);
        }

        return goodsRepository.saveAll(goodsList);
    }
}
