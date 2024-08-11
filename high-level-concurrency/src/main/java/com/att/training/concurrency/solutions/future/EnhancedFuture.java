package com.att.training.concurrency.solutions.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class EnhancedFuture {

    private static final ScheduledExecutorService scheduler = buildScheduler();

    private static ScheduledExecutorService buildScheduler() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .build();

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory);
        executor.setRemoveOnCancelPolicy(true);
        return executor;
    }

    static <T> CompletableFuture<T> orTimeout(CompletableFuture<T> future, long delay, TimeUnit unit) {
        ScheduledFuture<?> timeout = scheduler.schedule(() -> timeOut(future), delay, unit);
        return future.whenComplete((t, throwable) -> timeout.cancel(false));
    }

    private static void timeOut(CompletableFuture<?> future) {
        if (!future.isDone()) {
            future.completeExceptionally(new TimeoutException());
        }
    }
}
