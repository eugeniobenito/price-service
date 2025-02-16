package dev.eugeniobenito.price_service.price.infrastructure.persistence.entity;

import org.mapstruct.Mapper;
import dev.eugeniobenito.price_service.price.domain.Price;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {
    Price toDomain(PriceEntity priceEntity);

    default Optional<Price> toOptionalDomain(Optional<PriceEntity> optionalPriceEntity) {
        return optionalPriceEntity.map(this::toDomain);
    }
}
