package com.xplit.leboncoin.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidAdInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The AdsRepository class provides methods to read ad data from a JSON file.
 *
 * @version 1.0
 */
public class AdsRepository {

    /**
     * Reads ad data from the specified JSON file and returns a list of ads.
     * Each ad is assigned a random user from the provided user list.
     * If an error occurs during reading or deserialization, an appropriate message is displayed.
     *
     * @param path the path to the JSON file containing ad data
     * @param users the list of users to assign ads to
     * @return a list of ads
     * @throws IOException if there is an error reading the file
     */
    public static List<Ad> readAdsFromFile(String path, List<User> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        List<Ad> ads = new ArrayList<>();

        try (JsonParser parser = mapper.createParser(file)) {
            // Check if the JSON starts with an array
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("(Ad) Expected start of array in the JSON file.");
            }

            ObjectReader reader = mapper.readerFor(Ad.class);

            // Loop through the JSON array and deserialize each ad
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                Ad ad = null;
                try {
                    // Assign a random user to the ad
                    Random random = new Random();
                    int randomUser = random.nextInt(users.size());

                    // Deserialize the ad from the JSON
                    ad = reader.readValue(parser);

                    // Set the region of the ad based on the randomly selected user's region
                    ad.setRegion(users.get(randomUser).getRegion());

                    // Validate the ad
                    ad.isValidAd();

                    // Set the owner of the ad to the randomly selected user's ID
                    ad.setOwner(users.get(randomUser).getId());

                    // Add the ad to the list
                    ads.add(ad);
                } catch (InvalidAdInformations e) {
                    // Handle invalid ad information
                    System.out.println(TerminalColor.RED + e.getMessage() + ad + TerminalColor.RESET);
                } catch (JsonParseException e) {
                    // Handle JSON parsing exceptions
                    System.out.println(TerminalColor.RED + "(Ad) Error of deserialization (Check json type) : " + e.getMessage() + TerminalColor.RESET);
                    break;
                } catch (Exception e) {
                    // Handle any other exceptions during deserialization
                    System.out.println(TerminalColor.RED + "(Ad) Error of deserialization (Check json type) : " + e.getMessage() + TerminalColor.RESET);
                }
            }
        } catch (IOException e) {
            // Handle file opening or reading errors
            System.err.println("(Ad) Cannot open or read the file: " + e.getMessage());
            throw e;
        }

        return ads;
    }
}
