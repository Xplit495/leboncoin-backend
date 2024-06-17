package com.xplit.leboncoin.util;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.repository.AdsRepository;
import com.xplit.leboncoin.repository.UsersRepository;

import java.util.List;

/**
 * The DataInitializer class provides methods to initialize user and ad data
 * from JSON files.
 */
public class DataInitializer {

    /**
     * Initializes a list of users from a JSON file.
     *
     * @param path The path to the JSON file containing the users
     * @return     A list of initialized users
     */
    public static List<User> initializeUsers(String path) {
        try {
            return UsersRepository.readUsersFromFile(path);
        } catch (Exception e) {
            System.err.println("Error while initializing users: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    /**
     * Initializes a list of ads from a JSON file.
     *
     * @param path   The path to the JSON file containing the ads
     * @param users  The list of users to assign the ads to
     * @return       A list of initialized ads
     */
    public static List<Ad> initializeAds(String path, List<User> users) {
        try {
            return AdsRepository.readAdsFromFile(path, users);
        } catch (Exception e) {
            System.err.println("Error while initializing ads: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
