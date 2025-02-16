package dev.eugeniobenito.price_service.price.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<Price> findByTimeProductAndBrand(int productId, int brandId, LocalDateTime applicationDate);
}
