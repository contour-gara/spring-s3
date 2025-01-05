package org.contourgara.presentation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.vavr.control.Either;
import org.contourgara.application.DailySummaryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SpringS3ApplicationRunnerTest {
    @Mock
    DailySummaryUseCase dailySummaryUseCase;

    @InjectMocks
    SpringS3ApplicationRunner sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 実行時にユースケースが呼ばれる() {
        // set up
        doReturn(Either.right(null)).when(dailySummaryUseCase).execute();

        // execute
        sut.run(null);

        // assert
        verify(dailySummaryUseCase, times(1)).execute();
    }

    @Test
    void ユースケースで予期するエラーが発生した場合例外を返す() {
        // set up
        doReturn(Either.left(new DailySummaryUseCase.FindError("message"))).when(dailySummaryUseCase).execute();

        // execute & assert
        assertThatThrownBy(() -> sut.run(null))
                .isInstanceOf(SpringS3ApplicationRunner.DailySummaryUseCaseErrorException.class)
                .hasMessage("message");
    }
}
