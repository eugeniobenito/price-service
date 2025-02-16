package dev.eugeniobenito.price_service.price.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.eugeniobenito.price_service.price.infrastructure.persistence.entity.PriceEntity;

import java.util.Optional;
import java.time.LocalDateTime;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {
    @Query(value = "SELECT * FROM PRICES p "
            + "WHERE p.product_id = :productId "
            + "AND p.brand_id = :brandId "
            + "AND :applicationDate BETWEEN p.start_date AND p.end_date "
            + "ORDER BY p.priority DESC "
            + "LIMIT 1",
            nativeQuery = true)
    Optional<PriceEntity> findByTimeProductAndBrand(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") int productId,
            @Param("brandId") int brandId
    );
}
