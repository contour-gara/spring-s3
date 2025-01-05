package org.contourgara.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.contourgara.application.DailySummaryUseCase;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SpringS3ApplicationRunner implements ApplicationRunner {
    private final DailySummaryUseCase dailySummaryUseCase;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Execute Batch");
        dailySummaryUseCase.execute()
                .getOrElseThrow(DailySummaryUseCaseErrorException::new);
    }

    public static class DailySummaryUseCaseErrorException extends RuntimeException {
        public DailySummaryUseCaseErrorException(DailySummaryUseCase.Error dailySummaryUseCaseError) {
            super(dailySummaryUseCaseError.message());
        }
    }
}
