package com.att.training.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.att.training.concurrency.Utils.shutdownAndAwaitTermination;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slow
class Schedulers {

    private ScheduledExecutorService scheduledExecutor;

    @AfterEach
    void afterEach() {
        shutdownAndAwaitTermination(scheduledExecutor);
    }

    @Test
    void runOnce() {
        scheduledExecutor = newSingleThreadScheduledExecutor();

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = scheduledExecutor.schedule(task, 3, SECONDS);

        sleepUninterruptibly(1, SECONDS);

        long remainingDelay = future.getDelay(MILLISECONDS);
        System.out.printf("Remaining Delay: %sms%n", remainingDelay);

        keepJvmAliveFor(2, SECONDS);
    }

    @Test
    void runPeriodically() {
        scheduledExecutor = newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

        int initialDelay = 0;
        int period = 1;
        scheduledExecutor.scheduleAtFixedRate(task, initialDelay, period, SECONDS);

        keepJvmAliveFor(5, SECONDS);
    }

    @Test
    void runPeriodicallyWithDaemonThreads() {
        System.out.println("Hello!");
        scheduledExecutor = newScheduledThreadPool(1, new ThreadFactoryBuilder()
                .setDaemon(true)
                .build());

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

        int initialDelay = 0;
        int period = 1;
        scheduledExecutor.scheduleAtFixedRate(task, initialDelay, period, SECONDS);

        keepJvmAliveFor(5, SECONDS);
    }

    @Test
    void runLongTaskAtFixedRate() {
        scheduledExecutor = newScheduledThreadPool(1, new ThreadFactoryBuilder()
                .setNameFormat("fixed-rate-scheduler-pool-%d")
                .build());

        Runnable task = () -> {
            System.out.printf("%s - Starting task1: %s%n", LocalTime.now(), Thread.currentThread().getName());
            sleepUninterruptibly(2, SECONDS);
            System.out.printf("%s - Done1: %s%n", LocalTime.now(), Thread.currentThread().getName());
        };
        Runnable task2 = () -> {
            System.err.printf("%s - Starting task2: %s%n", LocalTime.now(), Thread.currentThread().getName());
            sleepUninterruptibly(2, SECONDS);
            System.err.printf("%s - Done2: %s%n", LocalTime.now(), Thread.currentThread().getName());
        };

        //  If any execution of this task takes longer than its period, then subsequent executions may start late, but will not concurrently execute.
        int initialDelay = 0;
        int period = 1;
        scheduledExecutor.scheduleAtFixedRate(task, initialDelay, period, SECONDS);
        scheduledExecutor.scheduleAtFixedRate(task2, initialDelay, period, SECONDS);

        keepJvmAliveFor(10, SECONDS);
    }

    @Test
    void runLongTaskWithFixedDelay() {
        scheduledExecutor = newScheduledThreadPool(1, new ThreadFactoryBuilder()
                .setNameFormat("fixed-delay-scheduler-pool-%d")
                .build());

        Runnable task = () -> {
            System.out.printf("%s - Starting task: %s%n", LocalTime.now(), Thread.currentThread().getName());
            sleepUninterruptibly(1, SECONDS);
            System.out.printf("%s - Done: %s%n", LocalTime.now(), Thread.currentThread().getName());
        };

        int initialDelay = 0;
        int delay = 2;
        scheduledExecutor.scheduleWithFixedDelay(task, initialDelay, delay, SECONDS);

        keepJvmAliveFor(10, SECONDS);
    }

    private static void keepJvmAliveFor(int count, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(count);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
