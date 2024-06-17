package com.xplit.leboncoin.util;

/**
 * The InvalidAdInformations class is an exception that is thrown
 * when an advertisement contains invalid information.
 */
public class InvalidAdInformations extends RuntimeException {
    /**
     * Constructs a new InvalidAdInformations exception with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception
     */
    public InvalidAdInformations(final String message) {
        super("\nERROR: The ad was skipped due to: " + message + '\n');
    }
}
