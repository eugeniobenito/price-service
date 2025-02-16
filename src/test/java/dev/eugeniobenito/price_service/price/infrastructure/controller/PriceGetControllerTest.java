package dev.eugeniobenito.price_service.price.infrastructure.controller;

import dev.eugeniobenito.price_service.price.application.find.FindPriceResponse;
import dev.eugeniobenito.price_service.price.application.find.PriceFinder;
import dev.eugeniobenito.price_service.shared.domain.exception.ApplicationException;
import dev.eugeniobenito.price_service.shared.domain.exception.PriceError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(PriceGetController.class)
public class PriceGetControllerTest {
    private static final int PRODUCT_ID = 35455;
    private static final int BRAND_ID = 1;
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.parse("2020-06-14T10:00:00");
    private static final String BASE_URL = "/api/price";
    private static final String API_URL = buildApiUrl(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PriceFinder priceFinder;

    private static String buildApiUrl(int brandId, int productId, LocalDateTime applicationDate) {
        return UriComponentsBuilder.fromPath(BASE_URL)
                .queryParam("brandId", brandId)
                .queryParam("productId", productId)
                .queryParam("applicationDate", applicationDate)
                .toUriString();
    }

    @Test
    void shouldReturnPriceResponseWhenValidParametersAreProvided() throws Exception {
        // GIVEN
        FindPriceResponse expectedResponse = new FindPriceResponse(
                PRODUCT_ID,
                BRAND_ID,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                new BigDecimal("35.5")
        );

        when(priceFinder.findPriceByTimeAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(expectedResponse);

        // WHEN & THEN
        mockMvc.perform(get(API_URL))
            .andExpect(status().isOk())
            .andExpect(content().json(
                    "{\"productId\":35455, " +
                    "\"brandId\":1, " +
                    "\"startDate\":\"2020-06-14T00:00:00\", " +
                    "\"endDate\":\"2020-12-31T23:59:59\", " +
                    "\"price\":35.5}"));

        verify(priceFinder, times(1))
                .findPriceByTimeAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
    }

    @Test
    void shouldReturn404NotFoundWhenNoPriceIsFound() throws Exception {
        // GIVEN
        when(priceFinder.findPriceByTimeAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenThrow(new ApplicationException(PriceError.PRICE_NOT_FOUND));

        // WHEN & THEN
        mockMvc.perform(get(API_URL))
                .andExpect(status().isNotFound());

        verify(priceFinder, times(1))
                .findPriceByTimeAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
    }
}
