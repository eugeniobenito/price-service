package dev.eugeniobenito.price_service.price.infrastructure.persistence;

import dev.eugeniobenito.price_service.price.domain.Price;
import dev.eugeniobenito.price_service.price.domain.PriceRepository;
import dev.eugeniobenito.price_service.price.infrastructure.persistence.entity.PriceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class H2PriceRepository implements PriceRepository {
    private final JpaPriceRepository jpaPriceRepository;

    @Override
    public Optional<Price> findByTimeProductAndBrand(int productId, int brandId, LocalDateTime applicationDate) {
        Optional<PriceEntity> priceEntity = jpaPriceRepository.findByTimeProductAndBrand(applicationDate, productId, brandId);

        return priceEntity.map(entity -> Price.builder()
                .id(entity.getId())
                .brandId(entity.getBrandId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceList())
                .productId(entity.getProductId())
                .priority(entity.getPriority())
                .amount(entity.getAmount())
                .curr(entity.getCurr())
                .build());
    }
}
