package com.xplit.leboncoin.util;

public class InvalidUserInformations extends RuntimeException {
    public InvalidUserInformations(final String prompt) {
        super("\nERROR : The user was skipped due to : " + prompt + '\n');
    }
}
