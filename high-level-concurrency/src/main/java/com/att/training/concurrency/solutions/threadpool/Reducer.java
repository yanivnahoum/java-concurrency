package com.att.training.concurrency.solutions.threadpool;

import com.att.training.concurrency.exercises.common.CalculatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class Reducer {

    private final CalculatorService calculatorService;
    private final ExecutorService executorService;
    private final List<Future<Long>> tasks = new ArrayList<>();

    Reducer(CalculatorService calculatorService, ExecutorService executorService) {
        this.calculatorService = calculatorService;
        this.executorService = executorService;
    }

    void put(long x) {
        Future<Long> task = executorService.submit(() -> calculatorService.square(x));
        tasks.add(task);
    }

    long get() {
        executorService.shutdown();
        return tasks.stream()
                    .mapToLong(this::getOrThrow)
                    .sum();
    }

    private long getOrThrow(Future<Long> task) {
        try {
            return task.get();
        }
        catch (RuntimeException ex) {
            // Avoid wrapping
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}