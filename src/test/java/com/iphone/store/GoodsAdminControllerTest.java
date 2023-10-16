package com.iphone.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iphone.store.dto.GoodsDto;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = IphoneStoreApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class GoodsAdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void whenCreateGoodsWithAdminCredentials_thenStatusIsCreated() throws Exception {
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setTitle("iph100");
        goodsDto.setPrice(100.0);
        goodsDto.setStock(40);
        goodsDto.setOrderCode("iph100");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/goods/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(goodsDto)))
                .characterEncoding("utf-8")
                .header("Authorization", "admin:admin"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value("201"))
                .andExpect(jsonPath("$.statusMsg").value("Goods were successfully created"));

    }

    @Test
    public void whenCreateGoodsWithNonAdminCredentials_thenStatusIsForbidden() throws Exception {
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setTitle("iph100");
        goodsDto.setPrice(100.0);
        goodsDto.setStock(40);
        goodsDto.setOrderCode("iph100");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/goods/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(goodsDto)))
                        .characterEncoding("utf-8")
                        .header("Authorization", "admin:admin"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}
