package dev.eugeniobenito.price_service.shared.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PriceError {
    PRICE_NOT_FOUND("Price not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
