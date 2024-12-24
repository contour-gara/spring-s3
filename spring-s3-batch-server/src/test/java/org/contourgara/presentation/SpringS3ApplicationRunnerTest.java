package org.contourgara.presentation;

import static org.mockito.Mockito.*;

import org.contourgara.application.DailySummaryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class SpringS3ApplicationRunnerTest {
    @MockitoBean
    DailySummaryUseCase dailySummaryUseCase;

    @Autowired
    SpringS3ApplicationRunner sut;

    @Test
    void 起動時に実行されユースケースが呼ばれる() {
        // assert
        verify(dailySummaryUseCase, times(1)).execute();
    }
}
