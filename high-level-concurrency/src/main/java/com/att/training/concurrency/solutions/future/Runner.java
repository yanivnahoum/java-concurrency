package com.att.training.concurrency.solutions.future;

import static com.att.training.concurrency.solutions.future.EnhancedFuture.orTimeout;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.TimeUnit.SECONDS;

class Runner {

    public static void main(String[] args) {
        // Change the delay to 1s and make sure you time out.
        orTimeout(runAsync(Runner::executeRequest), 3, SECONDS).join();
    }

    private static void executeRequest() {
        sleepUninterruptibly(2, SECONDS);
        System.out.println("Done!");
    }
}
