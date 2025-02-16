package dev.eugeniobenito.price_service.price.infrastructure.persistence.entity;

import dev.eugeniobenito.price_service.price.domain.Price;
import dev.eugeniobenito.price_service.price.domain.PriceMother;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PriceEntityMapperTest {
    private final PriceEntityMapper priceEntityMapper = Mappers.getMapper(PriceEntityMapper.class);

    @Test
    void shouldMapPriceEntityToPrice() {
        // GIVEN
        PriceEntity priceEntity = PriceMother.randomPriceEntity();

        // WHEN
        Price price = priceEntityMapper.toDomain(priceEntity);

        // THEN
        assertAll(
            () -> assertEquals(priceEntity.getProductId(), price.getProductId()),
            () -> assertEquals(priceEntity.getBrandId(), price.getBrandId()),
            () -> assertEquals(priceEntity.getStartDate(), price.getStartDate()),
            () -> assertEquals(priceEntity.getEndDate(), price.getEndDate()),
            () -> assertEquals(priceEntity.getAmount(), price.getAmount())
        );
    }

    @Test
    void shouldMapPriceEntityToPriceWhenOptionalIsPresent() {
        // GIVEN
        PriceEntity priceEntity = PriceMother.randomPriceEntity();
        Optional<PriceEntity> optionalPriceEntity = Optional.of(priceEntity);

        // WHEN
        Optional<Price> optionalPrice = priceEntityMapper.toOptionalDomain(optionalPriceEntity);

        // THEN
        assertTrue(optionalPrice.isPresent());

        Price price = optionalPrice.get();

        assertAll(
            () -> assertEquals(priceEntity.getProductId(), price.getProductId()),
            () -> assertEquals(priceEntity.getBrandId(), price.getBrandId()),
            () -> assertEquals(priceEntity.getStartDate(), price.getStartDate()),
            () -> assertEquals(priceEntity.getEndDate(), price.getEndDate()),
            () -> assertEquals(priceEntity.getAmount(), price.getAmount())
        );
    }

    @Test
    void shouldMapEmptyOptionalToEmptyOptional() {
        // GIVEN
        Optional<PriceEntity> optionalPriceEntity = Optional.empty();

        // WHEN
        Optional<Price> optionalPrice = priceEntityMapper.toOptionalDomain(optionalPriceEntity);

        // THEN
        assertTrue(optionalPrice.isEmpty());
    }
}