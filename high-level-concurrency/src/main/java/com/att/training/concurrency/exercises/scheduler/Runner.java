package com.att.training.concurrency.exercises.scheduler;

import com.att.training.concurrency.exercises.common.SoundDevice;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import static com.att.training.concurrency.Utils.keepJvmAliveFor;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Runner {

    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .build();
        // TODO create a Scheduler that uses a thread factory which creates daemon threads
        ScheduledExecutorService scheduler = null;
        Beeper beeper = new Beeper(new SoundDevice(), scheduler);
        beeper.beepFor(5, SECONDS);
        keepJvmAliveFor(6, SECONDS);
    }
}
