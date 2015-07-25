package org.noviv.configr.exceptions;

/**
 * Exception when attempting the validate a Configr file.
 */
public class ConfigrValidationException extends RuntimeException {

    /**
     * Create a new Configr validation exception.
     *
     * @param msg Message.
     */
    public ConfigrValidationException(String msg) {
        super(msg);
    }
}
