package dev.eugeniobenito.price_service.price.infrastructure.persistence;

import dev.eugeniobenito.price_service.price.domain.Price;
import dev.eugeniobenito.price_service.price.domain.PriceMother;
import dev.eugeniobenito.price_service.price.infrastructure.persistence.entity.PriceEntity;
import dev.eugeniobenito.price_service.price.infrastructure.persistence.entity.PriceEntityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({H2PriceRepository.class, PriceEntityMapperImpl.class})
class H2PriceRepositoryIT {
    @Autowired
    private H2PriceRepository h2PriceRepository;
    @Autowired
    private JpaPriceRepository jpaPriceRepository;

    @BeforeEach
    void setUp() {
        jpaPriceRepository.deleteAll();
    }

    @Test
    void shouldFindPriceByTimeProductAndBrand() {
        // GIVEN
        PriceEntity priceEntity = PriceMother.randomPriceEntity();
        jpaPriceRepository.save(priceEntity);

        // WHEN
        Price actualPrice = h2PriceRepository.findByTimeProductAndBrand(
                priceEntity.getProductId(),
                priceEntity.getBrandId(),
                priceEntity.getStartDate()
        ).orElseThrow();

        // THEN
        assertAll(
                () -> assertEquals(priceEntity.getId(), actualPrice.getId()),
                () -> assertEquals(priceEntity.getBrandId(), actualPrice.getBrandId()),
                () -> assertEquals(priceEntity.getStartDate(), actualPrice.getStartDate()),
                () -> assertEquals(priceEntity.getEndDate(), actualPrice.getEndDate()),
                () -> assertEquals(priceEntity.getPriceList(), actualPrice.getPriceList()),
                () -> assertEquals(priceEntity.getProductId(), actualPrice.getProductId()),
                () -> assertEquals(priceEntity.getPriority(), actualPrice.getPriority()),
                () -> assertEquals(priceEntity.getAmount(), actualPrice.getAmount()),
                () -> assertEquals(priceEntity.getCurr(), actualPrice.getCurr())
        );
    }

    @Test
    void shouldReturnEmptyWhenNoPriceIsFound() {
        // GIVEN
        Price price = PriceMother.randomFromProductAndBrandId(1, 2);

        // WHEN
        Price actualPrice = h2PriceRepository.findByTimeProductAndBrand(
                price.getProductId(),
                price.getBrandId(),
                price.getStartDate()
        ).orElse(null);

        // THEN
        assertNull(actualPrice);
    }

    @Test
    void shouldReturnPriceWithHighestPriority() {
        // GIVEN
        int productId = 1;
        int brandId = 2;

        PriceEntity highPriority = PriceMother.randomPriceEntity();
        highPriority.setPriority(1);
        highPriority.setProductId(productId);
        highPriority.setBrandId(brandId);
        jpaPriceRepository.save(highPriority);

        PriceEntity lowPriority = PriceMother.randomPriceEntity();
        lowPriority.setPriority(0);
        lowPriority.setProductId(productId);
        lowPriority.setBrandId(brandId);
        jpaPriceRepository.save(lowPriority);

        // WHEN
        Price actualPrice = h2PriceRepository.findByTimeProductAndBrand(
                highPriority.getProductId(),
                highPriority.getBrandId(),
                highPriority.getStartDate()
        ).orElseThrow();

        // THEN
        assertEquals(highPriority.getId(), actualPrice.getId());
    }

    @Test
    void shouldReturnEmptyWhenApplicationDateIsOutsideRange() {
        // GIVEN
        PriceEntity priceEntity = PriceMother.randomPriceEntity();
        jpaPriceRepository.save(priceEntity);

        LocalDateTime applicationDate = priceEntity.getStartDate().minusDays(1);

        // WHEN
        Price actualPrice = h2PriceRepository.findByTimeProductAndBrand(
                priceEntity.getProductId(),
                priceEntity.getBrandId(),
                applicationDate
        ).orElse(null);

        // THEN
        assertNull(actualPrice);
    }
}
