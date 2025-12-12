package com.example.lab2.client;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.lab2.model.DistanceRequest;
import com.example.lab2.model.DistanceResponse;

@Component
public class Client {
    private final WebClient webClient;

    public Client(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<DistanceResponse> compute(double x1, double y1, double x2, double y2) {
        DistanceRequest request = new DistanceRequest(x1, y1, x2, y2);
        return webClient.post()
                .uri("/server/distance")
                .bodyValue(request)      
                .retrieve()
                .bodyToMono(DistanceResponse.class)
                .retry(3);
    }

    public Flux<DistanceResponse> computes(List<DistanceRequest> requests) {
        return webClient.post()
                .uri("/server/distances")
                .bodyValue(requests)
                .retrieve()
                .bodyToFlux(DistanceResponse.class)
                .retry(3);
    }
}

