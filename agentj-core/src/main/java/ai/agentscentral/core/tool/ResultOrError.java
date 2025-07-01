package ai.agentscentral.core.tool;

import ai.agentscentral.core.convertors.TriConvertor;
import ai.agentscentral.core.error.Error;
import jakarta.annotation.Nonnull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * ResultOrError
 * @param <R>
 * @param <E>
 * @param <T>
 *
 * @author Rizwan Idrees
 */
public class ResultOrError<R, E extends Error, T> {

    private final R result;
    private final E error;
    private String contextId;
    private String messageId;
    private TriConvertor<String, String, R, T> resultConvertor;

    private ResultOrError(R result, E error) {
        this.result = result;
        this.error = error;
    }

    private Optional<R> getResult() {
        return ofNullable(result);
    }

    private Optional<E> getError() {
        return ofNullable(error);
    }

    public ResultOrError<R, E, T> onResult(@Nonnull String contextId,
                                           @Nonnull String messageId,
                                           @Nonnull TriConvertor<String, String, R, T> convertor) {
        this.contextId = contextId;
        this.messageId = messageId;
        this.resultConvertor = convertor;
        return this;
    }

    public T orOnError(@Nonnull TriConvertor<String, String, E, T> convertor) {


        if (Objects.nonNull(result) && Objects.nonNull(resultConvertor)) {
            return resultConvertor.convert(contextId, messageId, result);
        }

        return ofNullable(error).map(e -> convertor.convert(contextId, messageId, e))
                .orElseThrow(() -> new NoSuchElementException("error or result missing"));
    }

    public Error orError() {
        return error;
    }


    public static <R, E extends Error, T> ResultOrError<R, E, T> ofResult(R result) {
        return new ResultOrError<>(result, null);
    }

    public static <R, E extends Error, T> ResultOrError<R, E, T> ofError(E error) {
        return new ResultOrError<>(null, error);
    }
}
