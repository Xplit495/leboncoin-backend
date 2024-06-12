package com.xplit.leboncoin;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.DataInitializer;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        try {
            List<User> users = DataInitializer.readUsersFromFile("src/main/resources/users.json");

            List<Ad> ads = DataInitializer.readAdsFromFile("src/main/resources/ads.json", users);

            System.out.println("Je liste les users :");
            for (User user : users) {
                System.out.println((user.toString()));
            }

            System.out.println("Je liste les annonces :");
            for (Ad ad : ads) {
                System.out.println((ad.toString()));
            }

        } catch (Exception e) {
            System.out.println("Application.java error JSON : " + e.getMessage());
        }
    }
}