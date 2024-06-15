package com.xplit.leboncoin.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidAdInformations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdsRepository {
    public static List<Ad> readAdsFromFile(String path, List<User> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        List<Ad> ads = new ArrayList<>();

        try (JsonParser parser = mapper.createParser(file)) {
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("(Ad) Expected start of array in the JSON file.");
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
                } catch (JsonParseException e) {
                    System.out.println("(Ad) Error of deserialization (Check json type) : " + e.getMessage());
                    break;
                }catch (Exception e) {
                    System.out.println("(Ad) Error of deserialization (Check json type) : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("(Ad) Cannot open or read the file: " + e.getMessage());
            throw e;
        }

        return ads;
    }
}