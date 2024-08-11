package com.att.training.concurrency.exercises.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class EnhancedFuture {

    private static final ScheduledExecutorService scheduler = buildScheduler();

    private static ScheduledExecutorService buildScheduler() {
        // TODO
        return null;
    }

    static <T> CompletableFuture<T> orTimeout(CompletableFuture<T> future, long delay, TimeUnit unit) {
        // TODO complete the specified future with a TimeoutException at the specified delay
        //  remember to cancel the timeout when the future completes on time
        return future;
    }

}
