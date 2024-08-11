package com.att.training.concurrency;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Utils {

    private Utils() {
        // No instances allowed
    }

    public static void shutdownAndAwaitTermination(ExecutorService executor) {
        MoreExecutors.shutdownAndAwaitTermination(executor, 30L, SECONDS);
    }
}
