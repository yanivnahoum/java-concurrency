package com.att.training.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class Schedulers {

    @Test
    void runOnce() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1200);

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("%nRemaining Delay: %sms%n", remainingDelay);
    }

    @Test
    void runPeriodically() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    @Test
    void runPeriodicallyWithDaemonThreads() {
        System.out.println("Hello!");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder()
                .setDaemon(true)
                .build());

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    @Test
    void runLongTaskAtFixedRate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder()
                .setNameFormat("demo-scheduler-pool-%d")
                .build());

        Runnable task = () -> {
            System.out.printf("%s - Starting task: %s%n", LocalTime.now(), Thread.currentThread().getName());
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            System.out.printf("%s - Done: %s%n", LocalTime.now(), Thread.currentThread().getName());
        };
        Runnable task2 = () -> {
            System.out.printf("%s - Starting task2: %s%n", LocalTime.now(), Thread.currentThread().getName());
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            System.out.printf("%s - Done2: %s%n", LocalTime.now(), Thread.currentThread().getName());
        };

        //  If any execution of this task takes longer than its period, then subsequent executions may start late, but will not concurrently execute.
        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(task2, initialDelay, period, TimeUnit.SECONDS);
    }

    @Test
    void runLongTaskWithFixedDelay() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder()
                .setNameFormat("demo-scheduler-pool-%d")
                .build());

        Runnable task = () -> {
            System.out.printf("%s - Starting task: %s%n", LocalTime.now(), Thread.currentThread().getName());
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            System.out.printf("%s - Done: %s%n", LocalTime.now(), Thread.currentThread().getName());
        };

        int initialDelay = 0;
        int delay = 2;
        executor.scheduleWithFixedDelay(task, initialDelay, delay, TimeUnit.SECONDS);
    }
}
