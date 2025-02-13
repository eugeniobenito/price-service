package dev.eugeniobenito.price_service.healthcheck.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public final class HealthCheckGetController {

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("OK");
    }
}
