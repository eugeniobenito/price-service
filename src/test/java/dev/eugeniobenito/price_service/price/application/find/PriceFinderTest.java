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

    @BeforeEach
    void setUp() {
        priceFinder = new PriceFinder(priceRepository);
    }

    @Test
    void shouldFindPriceByTimeAndBrand() {
        // GIVEN
        LocalDateTime applicationDate = LocalDateTime.now();
        int productId = 1;
        int brandId = 2;

        Price expectedPrice = PriceMother.randomFromProductAndBrandId(productId, brandId);

        when(priceRepository.findByTimeProductAndBrand(productId, brandId, applicationDate))
                .thenReturn(Optional.of(expectedPrice));

        // WHEN
        FindPriceResponse actualPrice =
                assertDoesNotThrow(() -> priceFinder.findPriceByTimeAndBrand(productId, brandId, applicationDate));

        // THEN
        assertAll(
            () -> assertEquals(expectedPrice.getAmount(), actualPrice.price()),
            () -> assertEquals(expectedPrice.getBrandId(), actualPrice.brandId()),
            () -> assertEquals(expectedPrice.getProductId(), actualPrice.productId()),
            () -> assertEquals(expectedPrice.getStartDate(), actualPrice.startDate()),
            () -> assertEquals(expectedPrice.getEndDate(), actualPrice.endDate())
        );
        verify(priceRepository, times(1)).findByTimeProductAndBrand(productId, brandId, applicationDate);
    }

    @Test
    void shouldReturnNullWhenNoPriceIsFound() {
        // GIVEN
        LocalDateTime applicationDate = LocalDateTime.now();
        int productId = 1;
        int brandId = 2;

        when(priceRepository.findByTimeProductAndBrand(productId, brandId, applicationDate))
                .thenReturn(Optional.empty());

        // WHEN
        FindPriceResponse actualPrice = priceFinder.findPriceByTimeAndBrand(productId, brandId, applicationDate);

        // THEN
        assertNull(actualPrice);
        verify(priceRepository, times(1)).findByTimeProductAndBrand(productId, brandId, applicationDate);
    }
}