package com.xplit.leboncoin.util;

public class InvalidUserInformations extends RuntimeException {
    public InvalidUserInformations(final String message) {
        super("\nERROR: The user was skipped due to: " + message + '\n');
    }
}
