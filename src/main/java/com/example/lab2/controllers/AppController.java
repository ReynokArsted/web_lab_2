package com.example.lab2.controllers;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import com.example.lab2.model.CoordinatesRequest;
import com.example.lab2.model.DistanceResponse;
import com.example.lab2.service.DistanceService;

@RestController
@RequestMapping("/")
public class AppController {

    private final DistanceService distanceService;

    public AppController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping("test") // Get test
    public Mono<String> getString()
    {
        return Mono.just("It is a test!");
    }

    @PostMapping("destination")
    public Mono<DistanceResponse> calculateDistance(@RequestBody CoordinatesRequest request) {
        return Mono.fromCallable(() -> {
            BigDecimal distance = distanceService.calculate(request);
            return new DistanceResponse(distance);
        });
    }
}
