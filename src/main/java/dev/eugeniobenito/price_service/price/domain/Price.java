package dev.eugeniobenito.price_service.price.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// TODO: Remove lombok annotations?
@Builder
@Getter
@Setter
public class Price {
    private Long id;
    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Integer productId;
    private Integer priority;
    private BigDecimal amount;
    private String curr;
}