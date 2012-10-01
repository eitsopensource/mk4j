package br.com.eits.m4j.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which manages the logs produced by the API on console
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 12/07/2012
 */
public class MkLogger {

    private final Logger logger;

    /**
     * The logger initialization...
     *
     * @param clazz the class to log
     */
    public MkLogger(Class<?> clazz) {
        logger = Logger.getLogger(clazz.getName());
    }

    /**
     * Log an error message
     *
     * @param message the message
     * @param exception the exception
     */
    public void error(String message, Throwable exception) {
        logger.log(Level.SEVERE, message, exception);
    }

    /**
     * Log an error without exception
     *
     * @param message the message
     */
    public void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    /**
     * Log an information message
     *
     * @param message the message
     */
    public void info(String message) {
        logger.log(Level.INFO, message);
    }

    /**
     * Log an warning
     *
     * @param message the message
     */
    public void warn(String message) {
        logger.log(Level.WARNING, message);
    }

    /**
     * Log an warning with one exception
     *
     * @param message the message
     * @param exception the exception
     */
    public void warn(String message, Throwable exception) {
        logger.log(Level.WARNING, message, exception);
    }
}
