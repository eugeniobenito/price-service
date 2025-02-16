package dev.eugeniobenito.price_service.price.application.find;

import dev.eugeniobenito.price_service.price.application.PriceMapper;
import dev.eugeniobenito.price_service.price.domain.Price;
import dev.eugeniobenito.price_service.price.domain.PriceMother;
import dev.eugeniobenito.price_service.price.domain.PriceRepository;
import dev.eugeniobenito.price_service.shared.domain.exception.ApplicationException;
import dev.eugeniobenito.price_service.shared.domain.exception.PriceError;
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
    @Mock
    private PriceMapper priceMapper;
    private PriceFinder priceFinder;

    private static final int PRODUCT_ID = 1;
    private static final int BRAND_ID = 2;
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        priceFinder = new PriceFinder(priceRepository, priceMapper);
    }

    @Test
    void shouldFindPriceByTimeAndBrand() {
        // GIVEN
        Price expectedPrice = PriceMother.randomFromProductAndBrandId(PRODUCT_ID, BRAND_ID);

        when(priceRepository.findByTimeProductAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Optional.of(expectedPrice));

        FindPriceResponse mappedResponse = new FindPriceResponse(
                expectedPrice.getProductId(),
                expectedPrice.getBrandId(),
                expectedPrice.getStartDate(),
                expectedPrice.getEndDate(),
                expectedPrice.getAmount()
        );

        when(priceMapper.toResponse(expectedPrice)).thenReturn(mappedResponse);

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
        verify(priceMapper, times(1)).toResponse(expectedPrice);
    }

    @Test
    void shouldThrowApplicationExceptionWhenNoPriceIsFound() {
        // GIVEN
        when(priceRepository.findByTimeProductAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE))
                .thenReturn(Optional.empty());

        // WHEN & THEN
        ApplicationException exception =
                assertThrows(ApplicationException.class, () -> priceFinder.findPriceByTimeAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE));

        assertEquals(PriceError.PRICE_NOT_FOUND.getMessage(), exception.getMessage());
        assertEquals(PriceError.PRICE_NOT_FOUND.getStatus(), exception.getStatus());
        verify(priceRepository, times(1)).findByTimeProductAndBrand(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
        verify(priceMapper, never()).toResponse(any());
    }
}