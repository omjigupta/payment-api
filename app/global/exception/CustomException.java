package global.exception;

import lombok.Getter;
import play.Logger;

import javax.annotation.Nonnull;

/**
 * Custom Exception class for exception
 *
 * @author omji
 */
public class CustomException extends RuntimeException {
    @Getter
    private String message;
    private static final Logger.ALogger logger = Logger.of(CustomException.class);

    public CustomException(@Nonnull final String message) {
        logger.error(message);
        this.message = message;
    }

    public CustomException(@Nonnull final Throwable exception, @Nonnull final String message) {
        logger.error(message, exception);
        this.message = message;
    }

    public CustomException(@Nonnull final Throwable exception) {
        logger.error(message, exception);
        this.message = exception.getMessage();
    }

}
