package com.att.training.concurrency;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Utils {

    private Utils() {
        // No instances allowed
    }

    public static void shutdownAndAwaitTermination(ExecutorService executor) {
        MoreExecutors.shutdownAndAwaitTermination(executor, 30L, SECONDS);
    }

    public static void keepJvmAliveFor(int count, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(count);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String currentThreadName() {
        return Thread.currentThread().getName();
    }
}
