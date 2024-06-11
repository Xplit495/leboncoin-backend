package com.xplit.leboncoin.util;

public class InvalidUserInformations extends RuntimeException {
    public InvalidUserInformations(final String message) {
        super("ERROR : The user was skipped due to : " + message + '\n');
    }
}
