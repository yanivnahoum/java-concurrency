package com.att.training.concurrency.solutions.scheduler;

import com.att.training.concurrency.exercises.common.SoundDevice;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Beeper {

    private final SoundDevice soundDevice;
    private final ScheduledExecutorService scheduler;

    public Beeper(SoundDevice soundDevice, ScheduledExecutorService scheduler) {
        this.soundDevice = soundDevice;
        this.scheduler = scheduler;
    }

    /**
     * Schedule beeping once per second for the specified time interval
     *
     * @param period beeping interval
     * @param unit   the time unit of the period parameter
     */
    public void beepFor(long period, TimeUnit unit) {
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(soundDevice::beep, 0L, 1L, SECONDS);
        scheduler.schedule(() -> future.cancel(true), period, unit);
    }
}
