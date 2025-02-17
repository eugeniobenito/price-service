package dev.eugeniobenito.price_service.price.application.find;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FindPriceResponse(
        Integer productId,
        Integer brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priceList,
        BigDecimal price,
        String currency) {
}
