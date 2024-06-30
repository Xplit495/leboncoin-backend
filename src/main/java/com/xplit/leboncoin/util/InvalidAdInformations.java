package com.xplit.leboncoin.util;

/**
 * The InvalidAdInformations class represents a custom exception
 * that is thrown when ad information is invalid and an ad cannot be created.
 * This class extends RuntimeException.
 *
 * @version 1.0
 */
public class InvalidAdInformations extends RuntimeException {

    /**
     * Constructs a new InvalidAdInformations exception with the specified detail message.
     *
     * @param message the detail message explaining why the ad information is invalid
     */
    public InvalidAdInformations(final String message) {
        super("\nERREUR : Impossible de créer l'annonce à cause de : " + message + '\n');
    }
}
