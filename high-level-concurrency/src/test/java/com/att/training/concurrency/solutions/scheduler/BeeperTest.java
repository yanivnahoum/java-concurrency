package com.att.training.concurrency.solutions.scheduler;

import com.att.training.concurrency.exercises.common.SoundDevice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BeeperTest {

    @Mock
    private SoundDevice soundDevice;
    @Mock
    private ScheduledExecutorService scheduler;
    @Mock
    private ScheduledFuture<?> future;
    @Captor
    private ArgumentCaptor<Runnable> beepTaskCaptor;
    @Captor
    private ArgumentCaptor<Callable<Boolean>> cancelTaskCaptor;

    @Test
    void beepFor() throws Exception {
        // Using the doReturn syntax due to the generic wildcard type ScheduledFuture<?>
        doReturn(future).when(scheduler)
                        .scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any(TimeUnit.class));
        Beeper beeper = new Beeper(soundDevice, scheduler);

        long period = 10L;
        TimeUnit unit = MINUTES;
        beeper.beepFor(period, unit);

        verify(scheduler).scheduleAtFixedRate(beepTaskCaptor.capture(), eq(0L), eq(1L), eq(SECONDS));
        verify(scheduler).schedule(cancelTaskCaptor.capture(), eq(period), eq(unit));
        runTasks();
        verify(soundDevice).beep();
        verify(future).cancel(true);
    }

    private void runTasks() throws Exception {
        Runnable beepTask = beepTaskCaptor.getValue();
        beepTask.run();
        Callable cancelTask = cancelTaskCaptor.getValue();
        cancelTask.call();
    }
}