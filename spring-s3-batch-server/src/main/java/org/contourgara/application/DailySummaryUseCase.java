package org.contourgara.application;

import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class DailySummaryUseCase {
    public Either<Error, String > execute() {
        return Either.right(null);
    }

    public sealed interface Error permits FindError, UplocddError {
        Exception exception();
    }

    public record FindError(Exception exception) implements Error {}

    public record UplocddError(Exception exception) implements Error {}
}
