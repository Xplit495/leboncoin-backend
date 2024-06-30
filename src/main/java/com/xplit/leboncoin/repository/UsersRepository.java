package com.xplit.leboncoin.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidUserInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The UsersRepository class provides methods to read user data from a JSON file.
 *
 * @version 1.0
 */
public class UsersRepository {

    /**
     * Reads user data from the specified JSON file and returns a list of users.
     * If an error occurs during reading or deserialization, an appropriate message is displayed.
     *
     * @param path the path to the JSON file containing user data
     * @return a list of users
     * @throws IOException if there is an error reading the file
     */
    public static List<User> readUsersFromFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);

        List<User> users = new ArrayList<>();

        try (JsonParser parser = mapper.createParser(file)) {
            // Check if the JSON starts with an array
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("(User) Expected start of array in the JSON file.");
            }

            ObjectReader reader = mapper.readerFor(User.class);

            // Loop through the JSON array and deserialize each user
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                User user = null;
                try {
                    user = reader.readValue(parser);
                    user.isValidUser(); // Validate the user

                    user.setId(UUID.randomUUID()); // Assign a unique ID to the user

                    users.add(user); // Add the user to the list
                } catch (InvalidUserInformations e) {
                    // Handle invalid user information
                    System.out.println(TerminalColor.RED + e.getMessage() + user + TerminalColor.RESET);
                } catch (JsonParseException e) {
                    // Handle JSON parsing exceptions
                    System.out.println(TerminalColor.RED + "(User) Error of deserialization (Check json type) : " + e.getMessage() + TerminalColor.RESET);
                    break;
                } catch (Exception e) {
                    // Handle any other exceptions during deserialization
                    System.out.println(TerminalColor.RED + "(User) Error of deserialization (Check json type or syntax) : " + e.getMessage() + TerminalColor.RESET);
                }
            }
        } catch (IOException e) {
            // Handle file opening or reading errors
            System.err.println("(User) Cannot open or read the file: " + e.getMessage());
            throw e;
        }

        return users;
    }
}
