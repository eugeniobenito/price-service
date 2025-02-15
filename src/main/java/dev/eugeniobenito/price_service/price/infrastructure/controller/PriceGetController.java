package dev.eugeniobenito.price_service.price.infrastructure.controller;

import dev.eugeniobenito.price_service.price.application.find.FindPriceResponse;
import dev.eugeniobenito.price_service.price.application.find.PriceFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PriceGetController {
    private final PriceFinder priceFinder;

    @GetMapping("/price")
    public ResponseEntity<FindPriceResponse> getPrice(@RequestParam int productId, @RequestParam int brandId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        FindPriceResponse priceResponse = priceFinder.findPriceByTimeAndBrand(productId, brandId, applicationDate);

        if (priceResponse == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(priceResponse);
    }
}
