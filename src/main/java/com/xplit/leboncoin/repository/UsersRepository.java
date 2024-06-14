package com.xplit.leboncoin.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidUserInformations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersRepository {
    public static List<User> readUsersFromFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        List<User> users = new ArrayList<>();

        try (JsonParser parser = mapper.createParser(file)) {
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("(User) Expected start of array in the JSON file.");
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
                }  catch (JsonParseException e) {
                    System.out.println("(User) Error of deserialization (Check json type) : " + e.getMessage());
                    break;
                } catch (Exception e) {
                    System.out.println("(User) Error of deserialization (Check json type or syntax) : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("(User) Cannot open or read the file: " + e.getMessage());
            throw e;
        }

        return users;
    }
}
