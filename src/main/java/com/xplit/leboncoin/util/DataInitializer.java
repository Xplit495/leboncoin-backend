package com.xplit.leboncoin.util;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.repository.AdsRepository;
import com.xplit.leboncoin.repository.UsersRepository;

import java.util.List;

public class DataInitializer {

    public static List<User> initializeUsers(String path) {
        try {
            return UsersRepository.readUsersFromFile(path);
        } catch (Exception e) {
            System.err.println("Error while initializing users: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

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
