package com.xplit.leboncoin.util;

/**
 * The InvalidUserInformations class represents a custom exception
 * that is thrown when user information is invalid and a user cannot be created.
 * This class extends RuntimeException.
 *
 * @version 1.0
 */
public class InvalidUserInformations extends RuntimeException {

    /**
     * Constructs a new InvalidUserInformations exception with the specified detail message.
     *
     * @param message the detail message explaining why the user information is invalid
     */
    public InvalidUserInformations(final String message) {
        super("\nERREUR: Impossible de créer l'utilisateur à cause de : " + message + '\n');
    }
}
