package com.att.training.concurrency.solutions.future;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static com.att.training.concurrency.Utils.currentThreadName;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;

class Futures {

    public static void main(String[] args) {
        Futures futures = new Futures();
        futures.printMessageLengthAsync().join();
    }

    private CompletableFuture<Void> printMessageLengthAsync() {
        return supplyAsync(this::getMessage)
                .thenApplyAsync(this::length, Executors.newWorkStealingPool())
                .thenAccept(this::println);
    }

    private String getMessage() {
        String message = "Hello World";
        logStart("getMessage", message);
        sleepUninterruptibly(1, SECONDS);
        return message;
    }

    private int length(String str) {
        int length = str.length();
        logStart("length", str, length);
        sleepUninterruptibly(1, SECONDS);
        return length;
    }

    private void println(int value) {
        logStart("print", value);
    }

    private static void logStart(String methodName, Object... params) {
        String paramsStr = toString(params);
        System.out.printf("[%s] #%s %s%n", currentThreadName(), methodName, paramsStr);
    }

    private static String toString(Object[] params) {
        return params.length == 0 ? "" : Arrays.toString(params);
    }
}
