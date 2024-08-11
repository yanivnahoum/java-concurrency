package com.att.training.concurrency.exercises.common;

public interface CalculatorService {

    default long square(long x) {
        return x * x;
    }
}
