package com.xplit.leboncoin.util;

public class InvalidAdInformations extends RuntimeException {
    public InvalidAdInformations(final String message) {
        super("\nERREUR : Impossible de créer l'annonce à cause de : " + message + '\n');
    }
}
