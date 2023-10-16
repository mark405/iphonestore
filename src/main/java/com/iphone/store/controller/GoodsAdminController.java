package com.iphone.store.controller;

import com.iphone.store.dto.GoodsDto;
import com.iphone.store.dto.ResponseDto;
import com.iphone.store.security.ISecurityService;
import com.iphone.store.service.IGoodsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping(path = "/api/admin/goods", produces = {MediaType.APPLICATION_JSON_VALUE})
public class GoodsAdminController {
    private final IGoodsService iGoodsService;
    private final ISecurityService iSecurityService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createGoods(@RequestBody List<GoodsDto> goodsDtoList,
                                                   @RequestHeader("Authorization") String authorization) {
        if (!iSecurityService.isAdmin(authorization)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto("403", "Incorrect credentials for admin stuff."));
        }

        iGoodsService.create(goodsDtoList);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Goods were successfully created"));
    }
}
