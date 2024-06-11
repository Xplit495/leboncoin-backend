package com.xplit.leboncoin.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
                Ad ad = reader.readValue(parser);
                if (isValidTitle(ad)) {
                    if (isValidDescription(ad)) {
                        if (isValidPictures(ad)) {
                            if (isValidPrice(ad)) {
                                if (isValidRegion(ad)) {
                                    if (isValidCategory(ad)) {
                                        if (isValidPublicationDate(ad)) {
                                                Random random = new Random();
                                                int randomUser = random.nextInt(users.size());
                                                ad.setPublishBy(users.get(randomUser).getId());
                                            ads.add(ad);
                                        } else {
                                            System.out.println("\nError the next ad was skipped check Publication Date" + ad);
                                        }
                                    } else {
                                        System.out.println("\nError the next ad was skipped check Category" + ad);
                                    }
                                } else {
                                    System.out.println("\nError the next ad was skipped check Region" + ad);
                                }
                            } else {
                                System.out.println("\nError the next ad was skipped check Price" + ad);
                            }
                        } else {
                            System.out.println("\nError the next ad was skipped check Pictures" + ad);
                        }
                    } else {
                        System.out.println("\nError the next ad was skipped check Description" + ad);
                    }
                } else {
                    System.out.println("\nError the next ad was skipped check Title" + ad);
                }
            }
        }

        return ads;
    }

    private static boolean isValidTitle(Ad ad) {
        String adTitle = ad.getTitle();
        return adTitle != null && !adTitle.isEmpty();
    }

    private static boolean isValidDescription(Ad ad) {
        String adDescription = ad.getDescription();
        return adDescription != null && !adDescription.isEmpty();
    }

    private static boolean isValidPictures(Ad ad) {
        String[] adPictures = ad.getPictures();
        boolean isValidPictures = true;

        if (adPictures != null && adPictures.length > 0) {
            String prefix = "image";
            for (String adPicture : adPictures) {
                if (isValidPictures) {
                    isValidPictures = adPicture.startsWith(prefix);
                } else {
                    break;
                }
            }
        }else {
            isValidPictures = false;
        }

        return isValidPictures;
    }

    private static boolean isValidPrice(Ad ad) {
        Integer adPrice = ad.getPrice();
        return adPrice != null && adPrice > 0 && adPrice < 100000;
    }

    private static boolean isValidRegion(Ad ad) {
        String adRegion = ad.getRegion();
        return adRegion != null && !adRegion.isEmpty();
    }

    private static boolean isValidCategory(Ad ad) {
        String adCategory = ad.getCategory();
        for (String category : Ad.categories){
            if (category.equals(adCategory)){
                return true;
            }
        }
        return false;
    }

    private static boolean isValidPublicationDate(Ad ad) {
        String adPublicationDate = ad.getPublicationDate();

        if (adPublicationDate == null || adPublicationDate.length() != 10) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate publicationDate = LocalDate.parse(adPublicationDate, formatter);

            LocalDate today = LocalDate.now();

            if (publicationDate.getYear() < 2023 || publicationDate.isAfter(today)) {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

}