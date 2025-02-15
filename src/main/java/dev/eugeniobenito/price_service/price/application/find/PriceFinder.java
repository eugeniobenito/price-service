package dev.eugeniobenito.price_service.price.application.find;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PriceFinder {

    public FindPriceResponse findPriceByTimeAndBrand(int productId, int brandId, LocalDateTime applicationDate) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
