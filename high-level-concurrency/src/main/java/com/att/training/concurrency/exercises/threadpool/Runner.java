package com.att.training.concurrency.exercises.threadpool;

import com.att.training.concurrency.exercises.common.SimpleCalculatorService;
import com.google.common.base.Stopwatch;

import java.util.stream.LongStream;

class Runner {

    public static void main(String[] args) {
        Stopwatch stopwatch = time(Runner::run);
        System.out.println("Run duration: " + stopwatch);
    }

    private static void run() {
        // Using the SleepyCalculatorService() will take a long time...
        Reducer reducer = new Reducer(new SimpleCalculatorService());
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
