package com.att.training.concurrency.solutions.threadpool2;

import com.att.training.concurrency.exercises.common.SleepyCalculatorService;
import com.google.common.base.Stopwatch;

import java.util.stream.LongStream;

import static java.util.concurrent.Executors.newFixedThreadPool;

class Runner {

    public static void main(String[] args) {
        Stopwatch stopwatch = time(Runner::run);
        System.out.println("Run duration: " + stopwatch);
    }

    private static void run() {
        Reducer reducer = new Reducer(new SleepyCalculatorService(), newFixedThreadPool(1000));
        LongStream.rangeClosed(1, 1000)
                  .forEach(reducer::put);
        System.out.println("Done! value=" + reducer.get());
    }

    private static Stopwatch time(Runnable runnable) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        runnable.run();
        return stopwatch.stop();
    }
}
