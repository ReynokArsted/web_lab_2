package com.example.lab2.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.lab2.client.Client;
import com.example.lab2.model.DistanceRequest;
import com.example.lab2.model.DistanceResponse;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final Client client;

    public ClientController(Client client) {
        this.client = client;
    }

    @PostMapping("/distance")
    public Mono<DistanceResponse> getDistance(@RequestBody DistanceRequest req) 
    {
        return client.compute
        (  
            req.getX1(),
            req.getY1(),
            req.getX2(),
            req.getY2()
        );
    }

    @PostMapping("/distances")
    public Flux<DistanceResponse> getDistances(@RequestBody List<DistanceRequest> reqs) 
    {
        return client.computes(reqs);
    }
}
