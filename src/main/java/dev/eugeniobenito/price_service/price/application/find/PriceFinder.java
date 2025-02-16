package dev.eugeniobenito.price_service.price.application.find;

import dev.eugeniobenito.price_service.price.application.PriceMapper;
import dev.eugeniobenito.price_service.price.domain.Price;
import dev.eugeniobenito.price_service.price.domain.PriceRepository;
import dev.eugeniobenito.price_service.shared.domain.exception.PriceError;
import dev.eugeniobenito.price_service.shared.domain.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceFinder {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public FindPriceResponse findPriceByTimeAndBrand(int productId, int brandId, LocalDateTime applicationDate) {
        log.info("Finding product {}price for brand {} at {}", productId, brandId, applicationDate);

        Optional<Price> price = priceRepository.findByTimeProductAndBrand(productId, brandId, applicationDate);

        if (price.isEmpty()) {
            log.warn("Price not found for product {} and brand {} at {}", productId, brandId, applicationDate);
            throw new ApplicationException(PriceError.PRICE_NOT_FOUND);
        }

        return price.map(priceMapper::toResponse).orElse(null);
    }
}
