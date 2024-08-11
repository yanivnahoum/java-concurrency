package com.att.training.concurrency.solutions.threadpool;

import com.att.training.concurrency.Utils;
import com.att.training.concurrency.exercises.common.SleepyCalculatorService;
import com.google.common.base.Stopwatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

class Runner {

    public static void main(String[] args) {
        Stopwatch stopwatch = time(Runner::run);
        System.out.println("Run duration: " + stopwatch);
    }

    private static void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        runOn(executorService);
        Utils.shutdownAndAwaitTermination(executorService);
    }

    private static void runOn(ExecutorService executorService) {
        Reducer reducer = new Reducer(new SleepyCalculatorService(), executorService);
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
