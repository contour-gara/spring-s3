package org.contourgara.application;

import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class DailySummaryUseCase {
    public Either<Error, String > execute() {
        return Either.right(null);
    }

    public sealed interface Error permits FindError, UplocddError, ValidationError {
        String message();
    }
    public record FindError(String message) implements Error {}
    public record UplocddError(String message) implements Error {}
    public record ValidationError(String message) implements Error {}
}
