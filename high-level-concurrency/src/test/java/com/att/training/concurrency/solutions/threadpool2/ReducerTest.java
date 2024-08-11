package com.att.training.concurrency.solutions.threadpool2;

import com.att.training.concurrency.exercises.common.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.answer;
import static org.mockito.AdditionalAnswers.answerVoid;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReducerTest {

    @Mock
    private CalculatorService calculatorService;
    @Mock
    private ExecutorService executorService;

    @Test
    void putOf1to10_shouldReturn385() {
        when(calculatorService.square(anyLong())).thenAnswer(answer(this::square));
        doAnswer(answerVoid(this::runNow)).when(executorService).execute(any(Runnable.class));
        Reducer reducer = new Reducer(calculatorService, executorService);
        for (int i = 1; i <= 10; i++) {
            reducer.put(i);
        }
        long result = reducer.get();
        assertThat(result).isEqualTo(385);
    }

    private long square(long x) {
        return x * x;
    }

    private void runNow(Runnable runnable) {
        runnable.run();
    }
}