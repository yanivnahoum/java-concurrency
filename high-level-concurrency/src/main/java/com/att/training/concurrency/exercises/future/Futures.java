package com.att.training.concurrency.exercises.future;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static com.att.training.concurrency.Utils.currentThreadName;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.SECONDS;

class Futures {

    public static void main(String[] args) {
        Futures futures = new Futures();
        futures.printMessageLengthAsync().join();
    }

    private CompletableFuture<Void> printMessageLengthAsync() {
        // TODO run method this.getMessage() asynchronously, then map the returned string to its length
        //  by calling this.length(), and finally print the length by calling this.println()
        return null;
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
