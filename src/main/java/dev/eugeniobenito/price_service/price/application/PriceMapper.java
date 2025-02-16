package dev.eugeniobenito.price_service.price.application;

import dev.eugeniobenito.price_service.price.application.find.FindPriceResponse;
import dev.eugeniobenito.price_service.price.domain.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    @Mapping(target = "price", source = "amount")
    FindPriceResponse toResponse(Price price);
}
