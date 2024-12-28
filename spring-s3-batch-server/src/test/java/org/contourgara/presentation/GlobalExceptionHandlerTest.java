package org.contourgara.presentation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.ApplicationRunner;

class GlobalExceptionHandlerTest {
    @Mock
    SpringS3ApplicationRunner target;
    ApplicationRunner springS3ApplicationRunner;

    @Mock
    Appender<ILoggingEvent> appender;
    @Captor
    ArgumentCaptor<LoggingEvent> argumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
        logger.addAppender(appender);

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(new GlobalExceptionHandler());
        springS3ApplicationRunner = factory.getProxy();
    }

    @Test
    void バッチで例外が発生した場合ログが出力される() throws Exception {
        // set up
        doThrow(new RuntimeException("message")).when(target).run(null);

        // execute
        springS3ApplicationRunner.run(null);

        // assert
        verify(appender, times(1)).doAppend(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getLevel()).hasToString("ERROR");
        assertThat(argumentCaptor.getValue().getMessage()).isEqualTo("message");
    }
}
