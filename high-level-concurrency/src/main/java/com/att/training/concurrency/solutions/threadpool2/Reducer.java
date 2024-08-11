package com.att.training.concurrency.solutions.threadpool2;

import com.att.training.concurrency.Utils;
import com.att.training.concurrency.exercises.common.CalculatorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.LongAdder;

class Reducer {

    private final CalculatorService calculatorService;
    private final ExecutorService executorService;
    private final LongAdder value = new LongAdder();

    Reducer(CalculatorService calculatorService, ExecutorService executorService) {
        this.calculatorService = calculatorService;
        this.executorService = executorService;
    }

    void put(long x) {
        executorService.execute(() -> submitTask(x));
    }

    private void submitTask(long x) {
        long square = calculatorService.square(x);
        value.add(square);
    }

    long get() {
        Utils.shutdownAndAwaitTermination(executorService);
        return value.sum();
    }
}