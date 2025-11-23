package com.example.lab2.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class DistanceService {
    public Mono<Double> calculate(double x1, double y1, double x2, double y2) {
        return Mono.fromCallable(() -> {
            BigDecimal result = BigDecimal.ZERO;
            MathContext ro = new MathContext(20, RoundingMode.HALF_UP);

            // Пример неоптимальной логики
            for (int i = 0; i < 10000; i++) {
                BigDecimal bx1 = new BigDecimal(Double.toString(x1));
                BigDecimal by1 = new BigDecimal(Double.toString(y1));
                BigDecimal bx2 = new BigDecimal(Double.toString(x2));
                BigDecimal by2 = new BigDecimal(Double.toString(y2));

                BigDecimal dx = bx2.subtract(bx1, ro);
                BigDecimal dy = by2.subtract(by1, ro);

                Double boxedDx = Double.valueOf(dx.doubleValue());
                Double boxedDy = Double.valueOf(dy.doubleValue());
                dx = new BigDecimal(boxedDx.doubleValue());
                dy = new BigDecimal(boxedDy.doubleValue());

                BigDecimal dx2 = dx.pow(2, ro);
                BigDecimal dy2 = dy.pow(2, ro);
                BigDecimal sum = dx2.add(dy2, ro);
                result = sum.sqrt(MathContext.DECIMAL128);
            }
            return result.doubleValue();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
