/*
 * Mikrotik4J, Intregrate Java and Mikrotik RouterOS
 *
 * Copyright (c) 2012, Eits It Solutions and Arthur Gregório. 
 * or third-party contributors as indicated by the @author tags or express 
 * copyright attribution statements applied by the authors.  All third-party 
 * contributions are distributed under license by Eits It Solutions.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
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
