package com.att.training.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.lang.IO.println;
import static java.util.concurrent.Executors.newFixedThreadPool;

class Atomic {

    @Test
    void increment() {
        var atomicInt = new AtomicInteger();
        try (ExecutorService executor = newFixedThreadPool(2)) {

            for (int i = 0; i < 1_000_000; i++) {
                executor.submit(atomicInt::incrementAndGet);
            }
        }

        println(atomicInt.get());
    }

    @Test
    void updateAndGet() {
        var atomicInt = new AtomicInteger(0);
        try (ExecutorService executor = newFixedThreadPool(4)) {
            for (int i = 0; i < 1000000; i++) {
                executor.submit(() -> atomicInt.updateAndGet(n -> n + 2));
            }
        }

        println(atomicInt.get());
    }

    @Test
    void accumulateAndGet() {
        var atomicInt = new AtomicInteger();
        try (ExecutorService executor = newFixedThreadPool(2)) {
            IntStream.rangeClosed(1, 10)
                    .forEach(i -> executor.submit(() -> atomicInt.accumulateAndGet(i, Integer::sum)));
        }

        println("1 + 2 + ... + 10 = " + atomicInt.get());
    }

    @Test
    void atomicReference() {
        var atomicCounter = new AtomicReference<>(new Counter(0));
        var attemptCounter = new AtomicLong();

        Runnable incrementer = () -> {
            Counter current;
            Counter next;
            do {
                current = atomicCounter.get();
                next = new Counter(current.value() + 1);
                attemptCounter.incrementAndGet();

            } while (!atomicCounter.compareAndSet(current, next));
        };

        try (ExecutorService executor = newFixedThreadPool(10)) {
            IntStream.range(0, 1_000_000).forEach(i -> executor.submit(incrementer));
        }

        println("Counter: " + atomicCounter.get().value());
        println("Total # of attempts made: " + attemptCounter.get());
    }

    @Test
    void longAdder() {
        var adder = new LongAdder();
        try (ExecutorService executor = newFixedThreadPool(10)) {
            LongStream.rangeClosed(1, 1_000_000).forEach(i -> executor.execute(() -> adder.add(i)));
        }

        println("Counter: " + adder.sum());
    }

    /**
     * Immutable counter
     */
    record Counter(int value) {}
}
