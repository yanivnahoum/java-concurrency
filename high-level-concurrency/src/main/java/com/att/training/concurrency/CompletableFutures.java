package com.att.training.concurrency;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.att.training.concurrency.Utils.currentThreadName;
import static com.att.training.concurrency.Utils.daemonThreadFactory;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Slow
class CompletableFutures {
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4, daemonThreadFactory());
    private final Random random = new Random();

    @Test
    void runAsyncTask() {
        // Which thread does this run on?
        CompletableFuture<Void> future = CompletableFuture.runAsync(this::printInfo);
        future.join();
    }

    @Test
    void supplyAsyncTask() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(this::getInfo);
        System.out.println(future.join());
    }

    private void printInfo() {
        System.out.println(getInfo());
    }

    private String getInfo() {
        return "I'm currently running on: " + currentThreadName() + ". Is thread daemon? " + Thread.currentThread()
                                                                                                         .isDaemon();
    }

    @Test
    void helloWorldAsync() {
        CompletableFuture<String> futureString = getAsync("Hello Async World!");
        //        .handle(this::handleResult);
        //        .exceptionally(Throwable::toString);
        System.out.println(futureString.join());
    }

    private CompletableFuture<String> getAsync(String str) {
        CompletableFuture<String> future = new CompletableFuture<>();
        ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor(daemonThreadFactory());
        System.out.println("Will return your string soon....");

        scheduler.schedule(() -> {
            // Complete the future: with a result/exception, or even cancel it
            future.complete(str);
        }, 2, SECONDS);

        return future;
    }

    private String handleResult(String result, Throwable ex) {
        if (ex == null) {
            return "Got " + result;
        }
        else {
            return "An exception occurred: " + ex.getMessage();
        }
    }

    @Test
    void thenApplyAcceptAndRun() {
        // Which thread do chained methods run on?
        CompletableFuture<Void> f1 = supplyAsync(() -> sleepAndReturn(500, "1"))
                .thenApply(this::stringToInt)
                .thenAccept(this::println)
                .thenRun(this::printInfo);

//        supplyAsync(() -> sleepAndReturn(600, "2"))
//                .thenApplyAsync(this::stringToInt)
//                .thenAcceptAsync(this::println)
//                .join();

        f1.join();
//
//        supplyAsync(() -> sleepAndReturn(700, "3"), threadPool)
//                .thenApplyAsync(this::stringToInt, threadPool)
//                .thenAcceptAsync(this::println)
//                .join();
    }

    private int stringToInt(String str) {
        sleepUninterruptibly(500, MILLISECONDS);
        int parsed = Integer.parseInt(str);
        System.out.printf("[%s] - stringToInt: '%s' -> %d%n", currentThreadName(), str, parsed);
        return parsed;
    }

    private void println(Object obj) {
        sleepUninterruptibly(500, MILLISECONDS);
        System.out.printf("[%s] - println: %s%n", currentThreadName(), obj);
    }

    @Test
    void composingTasks() {
        supplyAsync(() -> sleepAndReturn(100, 123))
                .thenApply(Math::incrementExact)
                .thenCompose(this::intToStringAsync)
                .thenAccept(this::println)
                .join();
    }

    @Test
    void combiningTasks() {
        CompletableFuture<String> f1 = supplyAsync(() -> sleepAndReturn(50, "one"), threadPool);
        CompletableFuture<Integer> f2 = supplyAsync(() -> sleepAndReturn(200, 2));

        // The method that uses the two results can be run on f1's pool
        CompletableFuture<String> combinedFuture = f1.thenCombine(f2, (str, val) -> getInfo() + ", got " + str + " and " + val);
        System.out.println(combinedFuture.join());
    }

    @Test
    void allOf() {
        CompletableFuture<Integer> f1 = supplyAsync(() -> sleepAndReturn(500, 1));
        CompletableFuture<String> f2 = supplyAsync(() -> sleepAndReturn(1000, "2"));
        CompletableFuture<String> f3 = supplyAsync(() -> sleepAndReturn(200, "3"));

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);

        try {
            all.join();
        }
        catch (Exception e) {
            System.err.println("all - an exception occurred: " + e);
        }

        System.out.printf("Done. f1: %s, f3: %s%n", f1.join(), f3.join());

        String results = Stream.of(f1, f2, f3)
                               .filter(not(CompletableFuture::isCompletedExceptionally))
                               .map(cf -> cf.join().toString())
                               .collect(joining(", "));
        System.out.println("Results: " + results);
    }

    @Test
    void anyOf() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> sleepAndReturn(500, 1));
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> sleepAndReturn(1000, "2"));
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> sleepAndReturn(1500, "3"));

        CompletableFuture<Object> any = CompletableFuture.anyOf(f1, f2, f3);

        try {
            Object result = any.join();
            System.out.printf("Done. result: %s", result);
        }
        catch (Exception e) {
            System.err.println("An exception occurred: " + e);
        }
    }

    @Test
    void runMultipleTasks() {
        List<CompletableFuture<String>> futures = IntStream.range(1, 10)
                                                           .boxed()
                                                           .map(this::intToStringAsync)
                                                           .collect(toList());

        merge(futures)
                .thenAccept(System.out::println)
                .join();
    }

    private CompletableFuture<String> intToStringAsync(int i) {
        return supplyAsync(() -> sleepAndReturn(i, Integer.toString(i)))
                .exceptionally(Throwable::getMessage);
    }

    private <T> T sleepAndReturn(int ms, T obj) {
        sleepUninterruptibly(ms, MILLISECONDS);
        if (ms > 750) {
            throw new RuntimeException("Boom!");
        }
        System.out.printf("[%s] - Slept %dms, now returning %s as %s%n", currentThreadName(), ms, obj, obj.getClass()
                                                                                                                .getSimpleName());
        return obj;
    }

    @SuppressWarnings("unchecked")
    static <T> Predicate<T> not(Predicate<? super T> target) {
        Objects.requireNonNull(target);
        return (Predicate<T>) target.negate();
    }

    private static <T> CompletableFuture<List<T>> merge(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return all.thenApply(ignore -> futures.stream()
                                              .map(CompletableFuture::join)
                                              .collect(toList()));
    }

}
