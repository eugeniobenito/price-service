package dev.eugeniobenito.price_service.price.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
@Getter
@Setter
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "BRAND_ID")
    private Integer brandId;
    @Column(name = "PRODUCT_ID")
    private Integer productId;
    @Column(name = "START_DATE")
    private LocalDateTime startDate;
    @Column(name = "END_DATE")
    private LocalDateTime endDate;
    @Column(name = "PRICE_LIST")
    private Integer priceList;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "PRICE")
    private BigDecimal amount;
    @Column(name = "CURR")
    private String curr;
}
