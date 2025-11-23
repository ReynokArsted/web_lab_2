package com.example.lab2.model;

import java.math.BigDecimal;

public class DistanceResponse {
    private BigDecimal distance;

    public DistanceResponse(BigDecimal distance) {this.distance = distance;}
    
    public BigDecimal getDistance() {return distance;}
}

