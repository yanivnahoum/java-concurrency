package com.att.training.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.att.training.concurrency.Utils.shutdownAndAwaitTermination;
import static java.util.concurrent.Executors.newFixedThreadPool;

class Atomic {

    @Test
    void increment() {
        AtomicInteger atomicInt = new AtomicInteger();
        ExecutorService executor = newFixedThreadPool(2);

        IntStream.range(0, 1_000_000)
                 .forEach(i -> executor.submit(atomicInt::incrementAndGet));

        shutdownAndAwaitTermination(executor);

        System.out.println(atomicInt.get());
    }

    @Test
    void updateAndGet() {
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = newFixedThreadPool(4);

        IntStream.range(0, 1000000)
                 .forEach(i -> executor.submit(() -> atomicInt.updateAndGet(n -> n + 2)));

        shutdownAndAwaitTermination(executor);

        System.out.println(atomicInt.get());
    }

    @Test
    void accumulateAndGet() {
        AtomicInteger atomicInt = new AtomicInteger();
        ExecutorService executor = newFixedThreadPool(2);

        IntStream.rangeClosed(1, 10)
                 .forEach(i -> executor.submit(() -> atomicInt.accumulateAndGet(i, Integer::sum)));

        shutdownAndAwaitTermination(executor);

        System.out.println("1 + 2 + ... + 10 = " + atomicInt.get());
    }

    @Test
    void atomicReference() {
        ExecutorService executor = newFixedThreadPool(10);
        AtomicReference<Counter> atomicCounter = new AtomicReference<>(new Counter(0));
        AtomicLong attemptCounter = new AtomicLong();

        Runnable incrementer = () -> {
            Counter current;
            Counter next;
            do {
                current = atomicCounter.get();
                next = new Counter(current.getValue() + 1);
                attemptCounter.incrementAndGet();

            } while (!atomicCounter.compareAndSet(current, next));
        };

        IntStream.range(0, 1_000_000)
                 .forEach(i -> executor.submit(incrementer));

        shutdownAndAwaitTermination(executor);

        System.out.println("Counter: " + atomicCounter.get().getValue());
        System.out.println("Total # of attempts made: " + attemptCounter.get());
    }

    @Test
    void longAdder() {
        ExecutorService executor = newFixedThreadPool(10);
        LongAdder adder = new LongAdder();

        LongStream.rangeClosed(1, 1_000_000)
                  .forEach(i -> executor.execute(() -> adder.add(i)));

        shutdownAndAwaitTermination(executor);
        System.out.println("Counter: " + adder.sum());
    }
}

/**
 * Immutable counter
 */
class Counter {
    private int value;

    Counter(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}