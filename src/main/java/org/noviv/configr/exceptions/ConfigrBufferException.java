package org.noviv.configr.exceptions;

/**
 * Exception while writing to or flushing a Configr buffer.
 */
public class ConfigrBufferException extends RuntimeException {

    /**
     * Create a new Configr buffer exception.
     *
     * @param msg Message.
     */
    public ConfigrBufferException(String msg) {
        super(msg);
    }
}
