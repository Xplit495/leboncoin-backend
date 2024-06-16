package com.xplit.leboncoin.util;

public class InvalidAdInformations extends RuntimeException {
    public InvalidAdInformations(final String prompt) {
        super("\nERROR : The ad was skipped due to : " + prompt + '\n');
    }
}
