package com.xplit.leboncoin.util;

public class InvalidUserInformations extends RuntimeException {
    public InvalidUserInformations(final String message) {
        super("\nERREUR: Impossible de créer l'utilisateur à cause de : " + message + '\n');
    }
}
