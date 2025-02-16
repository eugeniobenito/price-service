package dev.eugeniobenito.price_service.price.application;

import dev.eugeniobenito.price_service.price.application.find.FindPriceResponse;
import dev.eugeniobenito.price_service.price.domain.Price;
import dev.eugeniobenito.price_service.price.domain.PriceMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceMapperTest {
    private final PriceMapper priceMapper = new PriceMapperImpl();

    @Test
    void shouldMapPriceToResponse() {
        // GIVEN
        Price price = PriceMother.randomFromProductAndBrandId(1, 2);

        // WHEN
        FindPriceResponse response = priceMapper.toResponse(price);

        // THEN
        assertAll(
            () -> assertEquals(price.getProductId(), response.productId()),
            () -> assertEquals(price.getBrandId(), response.brandId()),
            () -> assertEquals(price.getStartDate(), response.startDate()),
            () -> assertEquals(price.getEndDate(), response.endDate()),
            () -> assertEquals(price.getAmount(), response.price())
        );
    }
}