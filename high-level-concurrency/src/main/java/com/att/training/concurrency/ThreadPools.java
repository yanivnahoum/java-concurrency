package com.att.training.concurrency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.att.training.concurrency.Utils.shutdownAndAwaitTermination;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

class ThreadPools {

    private ExecutorService executor;

    @AfterEach
    void afterEach() {
        shutdownAndAwaitTermination(executor);
    }

    @Test
    void submitRunnableToThreadPool() {
        executor = newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            boolean daemon = Thread.currentThread().isDaemon();
            System.out.printf("Hello #1 from %s, daemon=%s%n", threadName, daemon);
            sleepUninterruptibly(2, SECONDS);
        });
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            boolean daemon = Thread.currentThread().isDaemon();
            System.out.printf("Hello #2 from %s, daemon=%s%n", threadName, daemon);
        });
    }

    @Test
    void submitCallableToThreadPool() {
        // Specify number of threads in pool
        executor = newFixedThreadPool(1);
        Future<Integer> future = executor.submit(ThreadPools::getInt);

        System.out.println("future done? " + future.isDone());

        Integer result = null;
        try {
            result = future.get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);

        shutdownAndAwaitTermination(executor);
    }

    private static Integer getInt() {
        String threadName = Thread.currentThread().getName();
        System.out.println("Hello " + threadName);
        sleepUninterruptibly(1, SECONDS);
        return 123;
    }

    @Test
    void getFutureWithTimeout() throws Exception {
        executor = newFixedThreadPool(1);

        Future<Integer> future = executor.submit(() -> {
            SECONDS.sleep(2);
            return 123;
        });

        // Decrease to 1 to see exception
        int result = future.get(3, SECONDS);
        System.out.println("Result: " + result);
    }

    @Test
    void invokeAll() throws InterruptedException {
        executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                // Set sleepSeconds to 0 or less to generate an ExecutionException
                buildCallable("task1", 2),
                buildCallable("task2", 1),
                buildCallable("task3", 3));

        System.out.println("Go!");
        // Should have been called waitForAll
        List<Future<String>> futures = executor.invokeAll(callables);

        futures.stream()
               .map(future -> {
                   String result;
                   try {
                       result = future.get();
                   }
                   catch (Exception e) {
                       e.printStackTrace();
                       result = "an exception was thrown";
                   }
                   return result;
               })
               .forEach(System.out::println);
    }

    @Test
    void invokeAny() throws InterruptedException, ExecutionException {
        executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                // Set sleepSeconds to 0 or less in ALL tasks to generate an ExecutionException
                buildCallable("task1", 0),
                buildCallable("task2", 0),
                buildCallable("task3", 1));

        String result = executor.invokeAny(callables);
        System.out.println("Result: " + result);
    }

    private static Callable<String> buildCallable(String result, long sleepSeconds) {
        return () -> {
            if (sleepSeconds > 0) {
                SECONDS.sleep(sleepSeconds);
                return result;
            }
            else {
                throw new IllegalArgumentException("Boom");
            }
        };
    }
}