package dev.eugeniobenito.price_service.price.infrastructure.controller;

import dev.eugeniobenito.price_service.price.application.find.FindPriceResponse;
import dev.eugeniobenito.price_service.price.application.find.PriceFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(PriceGetController.class)
public class PriceGetControllerTest {
    private static final String API_URL = "/api/price?brandId=1&productId=35455&applicationDate=2020-06-14T10:00:00";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PriceFinder priceFinder;

    private LocalDateTime applicationDate;
    private int productId;
    private int brandId;

    @BeforeEach
    void setUp() {
        applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        productId = 35455;
        brandId = 1;
    }

    @Test
    void shouldReturnPriceResponseWhenValidParametersAreProvided() throws Exception {
        // GIVEN
        FindPriceResponse expectedResponse = new FindPriceResponse(35455, 1, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), new BigDecimal("35.5"));

        when(priceFinder.findPriceByTimeAndBrand(productId, brandId, applicationDate))
                .thenReturn(expectedResponse);

        // WHEN & THEN
        mockMvc.perform(get(API_URL))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"productId\":35455,\"brandId\":1,\"startDate\":\"2020-06-14T00:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"price\":35.5}"));

        verify(priceFinder, times(1))
                .findPriceByTimeAndBrand(productId, brandId, applicationDate);
    }

    @Test
    void shouldReturn404NotFoundWhenNoPriceIsFound() throws Exception {
        // GIVEN
        when(priceFinder.findPriceByTimeAndBrand(productId, brandId, applicationDate))
                .thenReturn(null);

        // WHEN & THEN
        mockMvc.perform(get(API_URL))
                .andExpect(status().isNotFound());

        verify(priceFinder, times(1))
                .findPriceByTimeAndBrand(productId, brandId, applicationDate);
    }
}
