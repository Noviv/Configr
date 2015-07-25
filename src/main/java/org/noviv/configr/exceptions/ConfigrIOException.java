package org.noviv.configr.exceptions;

/**
 * Exception when running IO operations in Configr classes.
 */
public class ConfigrIOException extends RuntimeException {

    /**
     * Create a new Configr IO exception.
     * @param msg Message.
     */
    public ConfigrIOException(String msg) {
        super(msg);
    }
}
