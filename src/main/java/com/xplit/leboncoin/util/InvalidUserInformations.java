package com.xplit.leboncoin.util;

/**
 * The InvalidUserInformations class is an exception that is thrown
 * when a user contains invalid information.
 */
public class InvalidUserInformations extends RuntimeException {
    /**
     * Constructs a new InvalidUserInformations exception with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception
     */
    public InvalidUserInformations(final String message) {
        super("\nERROR: The user was skipped due to: " + message + '\n');
    }
}
