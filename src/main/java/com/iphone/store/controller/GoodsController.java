package com.iphone.store.controller;

import com.iphone.store.dto.GoodsDto;
import com.iphone.store.service.IGoodsService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/goods", produces = {MediaType.APPLICATION_JSON_VALUE})
public class GoodsController {
    private final IGoodsService iGoodsService;
    @GetMapping("/all")
    public List<GoodsDto> getAll() {
        return iGoodsService.findAll();
    }
}
