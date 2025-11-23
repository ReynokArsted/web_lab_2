package com.example.lab2.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.lab2.model.DistanceRequest;
import com.example.lab2.model.DistanceResponse;
import com.example.lab2.service.DistanceService;

@RestController
@RequestMapping("/server")
public class DistanceController {
    private final DistanceService service;

    public DistanceController(DistanceService service) {
        this.service = service;
    }

    @PostMapping("/distance")
    public Mono<DistanceResponse> compute(@RequestBody DistanceRequest req) {
        return service.calculate
        (
            req.getX1(), 
            req.getY1(), 
            req.getX2(), 
            req.getY2()
        ).map(dist -> new DistanceResponse(dist));
    }

    @PostMapping("/distances")
    public Flux<DistanceResponse> computes(@RequestBody List<DistanceRequest> requests) {
        return Flux.fromIterable(requests)
            .concatMap(req -> service.calculate
            (
                req.getX1(), 
                req.getY1(), 
                req.getX2(), 
                req.getY2()
            )).map(dist -> new DistanceResponse(dist));
    }
}
