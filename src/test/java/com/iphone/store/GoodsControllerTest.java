package com.iphone.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iphone.store.dto.GoodsDto;
import com.iphone.store.entity.Goods;
import com.iphone.store.repository.GoodsRepository;
import com.iphone.store.service.IGoodsService;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = IphoneStoreApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GoodsRepository goodsRepository;
    @Test
    public void givenGoods_whenGetGoods_thenStatus200() throws Exception {

        List<Goods> goods = goodsRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(5)) // Check the number of items in the response
                .andExpect(jsonPath("$[0].title").value(goods.get(0).getTitle()))
                .andExpect(jsonPath("$[1].title").value(goods.get(1).getTitle()));;
    }
}
