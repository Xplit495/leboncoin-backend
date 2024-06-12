package com.xplit.leboncoin.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.UUID;

public class DataInitializer {
    public static List<User> readUsersFromFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        List<User> users = new ArrayList<>();

        try (JsonParser parser = mapper.createParser(file)) {
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("Expected start of array in the JSON file.");
            }

            ObjectReader reader = mapper.readerFor(User.class);

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                User user = null;
                try {
                    user = reader.readValue(parser);
                    user.isValidUser();

                    user.setId(UUID.randomUUID());

                    users.add(user);
                } catch (InvalidUserInformations e) {
                    System.out.println(e.getMessage() + user);
                } catch (Exception e) {
                    System.out.println("Error of deserialization (Check json type) : " + e.getMessage());
                }
            }
        }

        return users;
    }

    public static List<Ad> readAdsFromFile(String path, List<User> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        List<Ad> ads = new ArrayList<>();

        try (JsonParser parser = mapper.createParser(file)) {
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("Expected start of array in the JSON file.");
            }

            ObjectReader reader = mapper.readerFor(Ad.class);

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                Ad ad = null;
                try {
                    ad = reader.readValue(parser);
                    ad.isValidAd();

                    Random random = new Random();
                    int randomUser = random.nextInt(users.size());
                    ad.setPublishBy(users.get(randomUser).getId());

                    ads.add(ad);
                } catch (InvalidAdInformations e) {
                    System.out.println(e.getMessage() + ad);
                } catch (Exception e) {
                    System.out.println("Error of deserialization (Check json type) : " + e.getMessage());
                }

            }
        }

        return ads;
    }
}