package com.example.lab2.service;

import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/distance")
public class DistanceService {

    @PostMapping
    public Mono<DistanceResponse> calculate(@RequestBody CoordinatesRequest request) {
        return Mono.fromCallable(() -> {
            // Неоптимальная логика (10k повторов, BigDecimal)
            BigDecimal result = BigDecimal.ZERO;
            for (int i = 0; i < 10000; i++) {
                BigDecimal dx = new BigDecimal(request.getX1()).subtract(new BigDecimal(request.getX2()));
                BigDecimal dy = new BigDecimal(request.getY1()).subtract(new BigDecimal(request.getY2()));
                result = sqrt(dx.multiply(dx).add(dy.multiply(dy)), new MathContext(20));
            }
            return new DistanceResponse(result);
        });
    }

    private BigDecimal sqrt(BigDecimal value, MathContext mc) {
        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = new BigDecimal(Math.sqrt(value.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = value.divide(x0, mc).add(x0).divide(BigDecimal.valueOf(2), mc);
        }
        return x1;
    }
}

class CoordinatesRequest {
    private double x1, y1, x2, y2;

    public double getX1() {return x1;} 
    public double getY1() {return y1;} 
    public double getX2() {return x2;} 
    public double getY2() {return y2;} 
    
    public void setX1(double x1) {this.x1 = x1;}
    public void setY1(double y1) {this.y1 = y1;}
    public void setX2(double x2) {this.x2 = x2;}
    public void setY2(double y2) {this.y2 = y2;}
}

class DistanceResponse {
    private BigDecimal distance;

    public DistanceResponse() {}
    public DistanceResponse(BigDecimal distance) {this.distance = distance;}

    public BigDecimal getDistance() {return distance;}
    public void setDistance(BigDecimal distance) {this.distance = distance;}
}
