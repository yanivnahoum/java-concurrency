package com.att.training.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.att.training.concurrency.Utils.shutdownAndAwaitTermination;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ThreadPools {

    public static void main(String[] args) {
        try {
//            submitRunnableToThreadPool();
            submitCallableToThreadPool();
//            getFutureWithTimeout();
//            invokeAll();
//            invokeAny();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void submitRunnableToThreadPool() {
        ExecutorService executor = newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            boolean daemon = Thread.currentThread().isDaemon();
            System.out.printf("Hello %s, daemon=%s%n", threadName, daemon);
            try {
                SECONDS.sleep(2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            boolean daemon = Thread.currentThread().isDaemon();
            System.out.printf("Hello 2 %s, daemon=%s%n", threadName, daemon);
        });
        
        shutdownAndAwaitTermination(executor);
    }

    private static void submitCallableToThreadPool() {
        Callable<Integer> task = () -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("Hello " + threadName);
                SECONDS.sleep(1);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
        
        // Specify number of threads in pool
        ExecutorService executor = newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);

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
    }

    private static void getFutureWithTimeout() throws Exception {
        ExecutorService executor = newFixedThreadPool(1);

        Future<Integer> future = executor.submit(() -> {
            try {
                SECONDS.sleep(2);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("Task interrupted!", e);
            }
        });

        // Decrease to 1 to see exception
        int result = future.get(1, SECONDS);
        System.out.println("Result: " + result);
    }

    private static void invokeAll() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                buildCallable("task1", 2),
                buildCallable("task2", 1),
                buildCallable("task3", 3));

        System.out.println("Go!");
        // Should have been waitForAll
        List<Future<String>> futures = executor.invokeAll(callables);
        
        futures.stream()
            .map(future -> {
                try {
                    return future.get();
                }
                catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            })
            .forEach(System.out::println);        
    }
    
    private static void invokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                buildCallable("task1", 2),
                buildCallable("task2", 1),
                buildCallable("task3", 3));

        String result = executor.invokeAny(callables);
        System.out.println("Result: " + result);      
    }
    
    private static Callable<String> buildCallable(String result, long sleepSeconds) {
        return () -> {
            SECONDS.sleep(sleepSeconds);
            return result;
        };        
    }
}