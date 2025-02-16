package dev.eugeniobenito.price_service.shared.domain.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ApplicationException extends RuntimeException {
    private final PriceError priceError;

    @Override
    public String getMessage() {
        return priceError.getMessage();
    }

    public HttpStatus getStatus() {
        return priceError.getStatus();
    }
}
