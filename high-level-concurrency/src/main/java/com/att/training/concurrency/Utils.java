package com.att.training.concurrency;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

class Utils {

    private Utils() {
        // No instances allowed
    }

    static void shutdownAndAwaitTermination(ExecutorService executor) {
        MoreExecutors.shutdownAndAwaitTermination(executor, 1L, SECONDS);
    }
}
