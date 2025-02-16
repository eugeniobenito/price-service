package dev.eugeniobenito.price_service.price.application.find;

import dev.eugeniobenito.price_service.price.domain.Price;
import dev.eugeniobenito.price_service.price.domain.PriceMother;
import dev.eugeniobenito.price_service.price.domain.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceFinderTest {
    @Mock
    private PriceRepository priceRepository;
    private PriceFinder priceFinder;

    private static final int PRODUCT_ID = 1;
    private static final int BRAND_ID = 2;
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        priceFinder = new PriceFinder(priceRepository);
    }

    @Test
    void shouldFindPriceByTimeAndBrand() {
        // GIVEN
        Price expectedPrice = PriceMother.randomFromProductAndBrandId(PRODUCT_ID, BRAND_ID);

        when(priceRepository.findByTimeProductAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Optional.of(expectedPrice));

        // WHEN
        FindPriceResponse actualPrice =
                assertDoesNotThrow(() -> priceFinder.findPriceByTimeAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE));

        // THEN
        assertAll(
            () -> assertEquals(expectedPrice.getAmount(), actualPrice.price()),
            () -> assertEquals(expectedPrice.getBrandId(), actualPrice.brandId()),
            () -> assertEquals(expectedPrice.getProductId(), actualPrice.productId()),
            () -> assertEquals(expectedPrice.getStartDate(), actualPrice.startDate()),
            () -> assertEquals(expectedPrice.getEndDate(), actualPrice.endDate())
        );
        verify(priceRepository, times(1)).findByTimeProductAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
    }

    @Test
    void shouldReturnNullWhenNoPriceIsFound() {
        // GIVEN
        when(priceRepository.findByTimeProductAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Optional.empty());

        // WHEN
        FindPriceResponse actualPrice = priceFinder.findPriceByTimeAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);

        // THEN
        assertNull(actualPrice);
        verify(priceRepository, times(1)).findByTimeProductAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
    }
}