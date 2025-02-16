package dev.eugeniobenito.price_service.price.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.*;
import dev.eugeniobenito.price_service.price.infrastructure.persistence.entity.PriceEntity;

public class PriceMother {
    public static Price randomFromProductAndBrandId(int productId, int brandId) {
        Faker faker = Faker.instance();

        Date startDate = faker.date().past(1, TimeUnit.DAYS);
        Date endDate = faker.date().future(1, TimeUnit.DAYS);

        return Price.builder()
                .id(faker.number().randomNumber())
                .startDate(toLocalDateTime(startDate))
                .endDate(toLocalDateTime(endDate))
                .productId(productId)
                .brandId(brandId)
                .priceList(faker.number().randomDigit())
                .priority(faker.number().randomDigit())
                .amount(BigDecimal.valueOf(faker.random().nextDouble()))
                .curr(faker.currency().code())
                .build();
    }

    public static PriceEntity randomPriceEntity() {
        Faker faker = new Faker();

        Date startDate = faker.date().past(1, TimeUnit.DAYS);
        Date endDate = faker.date().future(1, TimeUnit.DAYS);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(null);
        priceEntity.setBrandId(faker.number().randomDigit());
        priceEntity.setProductId(faker.number().randomDigit());
        priceEntity.setStartDate(toLocalDateTime(startDate));
        priceEntity.setEndDate(toLocalDateTime(endDate));
        priceEntity.setPriceList(faker.number().randomDigit());
        priceEntity.setPriority(faker.number().randomDigit());
        priceEntity.setAmount(BigDecimal.valueOf(faker.random().nextDouble()));
        priceEntity.setCurr(faker.currency().code());

        return priceEntity;
    }

    private static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}