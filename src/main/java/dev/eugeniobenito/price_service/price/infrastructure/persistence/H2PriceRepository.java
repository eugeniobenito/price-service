package dev.eugeniobenito.price_service.price.infrastructure.persistence;

import dev.eugeniobenito.price_service.price.domain.Price;
import dev.eugeniobenito.price_service.price.domain.PriceRepository;
import dev.eugeniobenito.price_service.price.infrastructure.persistence.entity.PriceEntity;
import dev.eugeniobenito.price_service.price.infrastructure.persistence.entity.PriceEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class H2PriceRepository implements PriceRepository {
    private final JpaPriceRepository jpaPriceRepository;
    private final PriceEntityMapper priceEntityMapper;

    @Override
    public Optional<Price> findByTimeProductAndBrand(int productId, int brandId, LocalDateTime applicationDate) {
        Optional<PriceEntity> priceEntity = jpaPriceRepository.findByTimeProductAndBrand(applicationDate, productId, brandId);

        return priceEntityMapper.toOptionalDomain(priceEntity);
    }
}
