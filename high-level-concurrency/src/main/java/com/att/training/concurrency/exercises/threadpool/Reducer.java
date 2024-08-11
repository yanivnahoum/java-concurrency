package com.att.training.concurrency.exercises.threadpool;

import com.att.training.concurrency.exercises.common.CalculatorService;

/**
 * Calculates the sum of squares put into it.
 * By calling put(1), put(2),...,put(n) and the get(), the value returned
 * is the nth pyramidal square number: Pn = (2n^3 + 3n^2 + n)/6
 * <br>
 * P10 = 1^2 + 2^2 + ... + 10^2 = 1 + 4 + ... + 100 = 385
 * <br>
 * P1000 = 333833500
 */
class Reducer {

    private final CalculatorService calculatorService;
    private long value;

    Reducer(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    // TODO make this method async - instead of calling the CalculatorService on the current thread,
    //  submit the task to a thread pool!
    void put(long x) {
        long square = calculatorService.square(x);
        value += square;
    }

    long get() {
        return value;
    }
}