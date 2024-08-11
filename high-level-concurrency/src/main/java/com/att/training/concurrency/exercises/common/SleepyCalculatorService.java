package com.att.training.concurrency.exercises.common;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SleepyCalculatorService implements CalculatorService {

    @Override
    public long square(long x) {
        try {
            SECONDS.sleep(5);
        }
        catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted!");
        }
        return CalculatorService.super.square(x);
    }
}
