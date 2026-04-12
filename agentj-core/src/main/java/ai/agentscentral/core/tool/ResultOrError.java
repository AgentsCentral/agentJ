package ai.agentscentral.core.tool;

import ai.agentscentral.core.convertors.TriConvertor;
import ai.agentscentral.core.error.Error;
import jakarta.annotation.Nonnull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * A discriminated union that holds either a successful result or a structured error,
 * with deferred conversion to a final output type.
 *
 * <p>Used by {@link ToolCallExecutor} to return the outcome of a tool invocation
 * without immediately converting it to a {@link ai.agentscentral.core.session.message.ToolMessage}.
 * The caller first calls {@link #onResult} to register the context id, message id, and
 * result convertor, then calls {@link #orOnError} to obtain the final converted value —
 * applying the result convertor if a result is present, or the error convertor otherwise.</p>
 *
 * <p>Instances are created via the static factories {@link #ofResult} and
 * {@link #ofError}.</p>
 *
 * @param <R> the type of the successful result (e.g. {@link ToolCallResult})
 * @param <E> the type of the structured error (e.g. {@link ToolCallExecutionError});
 *            must extend {@link ai.agentscentral.core.error.Error}
 * @param <T> the final output type produced by the convertors (e.g.
 *            {@link ai.agentscentral.core.session.message.ToolMessage})
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

    /**
     * Registers the context id, message id, and result convertor to use when a successful
     * result is present.
     *
     * <p>Must be called before {@link #orOnError}. Returns {@code this} for chaining.</p>
     *
     * @param contextId the conversation context id to pass to the convertor
     * @param messageId the message id to pass to the convertor
     * @param convertor the {@link TriConvertor} that transforms
     *                  {@code (contextId, messageId, result)} into a value of type {@code T}
     * @return this instance for chaining with {@link #orOnError}
     */
    public ResultOrError<R, E, T> onResult(@Nonnull String contextId,
                                           @Nonnull String messageId,
                                           @Nonnull TriConvertor<String, String, R, T> convertor) {
        this.contextId = contextId;
        this.messageId = messageId;
        this.resultConvertor = convertor;
        return this;
    }

    /**
     * Produces the final output value by applying either the result convertor (if a result
     * is present and {@link #onResult} has been called) or the error convertor.
     *
     * @param convertor the {@link TriConvertor} that transforms
     *                  {@code (contextId, messageId, error)} into a value of type {@code T}
     * @return the converted result or converted error
     * @throws java.util.NoSuchElementException if neither a result nor an error is set
     */
    public T orOnError(@Nonnull TriConvertor<String, String, E, T> convertor) {


        if (Objects.nonNull(result) && Objects.nonNull(resultConvertor)) {
            return resultConvertor.convert(contextId, messageId, result);
        }

        return ofNullable(error).map(e -> convertor.convert(contextId, messageId, e))
                .orElseThrow(() -> new NoSuchElementException("error or result missing"));
    }

    /**
     * Returns the raw error, or {@code null} if this instance holds a result.
     *
     * @return the error, or {@code null}
     */
    public Error orError() {
        return error;
    }

    /**
     * Creates a {@code ResultOrError} holding a successful result.
     *
     * @param <R>    the result type
     * @param <E>    the error type
     * @param <T>    the final output type
     * @param result the successful result value
     * @return a new instance with the result set and error {@code null}
     */
    public static <R, E extends Error, T> ResultOrError<R, E, T> ofResult(R result) {
        return new ResultOrError<>(result, null);
    }

    /**
     * Creates a {@code ResultOrError} holding a structured error.
     *
     * @param <R>   the result type
     * @param <E>   the error type
     * @param <T>   the final output type
     * @param error the error value
     * @return a new instance with the error set and result {@code null}
     */
    public static <R, E extends Error, T> ResultOrError<R, E, T> ofError(E error) {
        return new ResultOrError<>(null, error);
    }
}
