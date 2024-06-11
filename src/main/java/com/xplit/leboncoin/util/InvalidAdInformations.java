package com.xplit.leboncoin.util;

public class InvalidAdInformations extends RuntimeException {
    public InvalidAdInformations(final String message) {
        super("ERROR : The ad was skipped due to : " + message);
    }
}
