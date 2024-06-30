package com.xplit.leboncoin.util;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.repository.AdsRepository;
import com.xplit.leboncoin.repository.UsersRepository;

import java.util.List;

/**
 * The DataInitializer class provides methods to initialize user and ad data from files.
 *
 * @version 1.0
 */
public class DataInitializer {

    /**
     * Initializes and returns a list of users from the specified file path.
     * If an error occurs during initialization, the application will exit.
     *
     * @param path the file path to read user data from
     * @return a list of users, or null if an error occurs
     */
    public static List<User> initializeUsers(String path) {
        try {
            return UsersRepository.readUsersFromFile(path);
        } catch (Exception e) {
            // Print an error message and exit the application if an exception occurs
            System.err.println("Error while initializing users: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    /**
     * Initializes and returns a list of ads from the specified file path,
     * associating them with the provided list of users.
     * If an error occurs during initialization, the application will exit.
     *
     * @param path the file path to read ad data from
     * @param users the list of users to associate ads with
     * @return a list of ads, or null if an error occurs
     */
    public static List<Ad> initializeAds(String path, List<User> users) {
        try {
            return AdsRepository.readAdsFromFile(path, users);
        } catch (Exception e) {
            // Print an error message and exit the application if an exception occurs
            System.err.println("Error while initializing ads: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
