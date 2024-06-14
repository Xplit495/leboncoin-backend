package com.xplit.leboncoin.service;

import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidUserInformations;

import java.util.List;
import java.util.Scanner;

public class UserService {

    public static void showUsers(List<User> users) {
        for (int i = 0; i < 40; i++) {System.out.println('\n');}
        System.out.println("\nListe des utilisateurs :");
        users.forEach(System.out::println);
    }

    public static User createUser() {
        Scanner scanner = new Scanner(System.in);
        Integer age = null;

        String[] prompt = {"Prénom (Obligatoire)", "Nom (Facultatif)", "Nom d'utilisateur (Obligatoire)",
                "Mail (Obligatoire)", "Téléphone (Facultatif)", "Âge (Facultatif)" ,"Région (Obligatoire)"};
        String[] userInfo = new String[7];

        for (int i = 0; i < prompt.length; i++) {
            System.out.print(prompt[i] + " : ");
            String input = scanner.nextLine();

            if (input.isEmpty()){
                userInfo[i] = null;
            } else {
                userInfo[i] = input;
            }
        }

        try {
            age = Integer.parseInt(userInfo[5]);
        } catch (NumberFormatException ignored) {}

        User tempUser = new User (userInfo[0], userInfo[1], userInfo[2], userInfo[3], userInfo[4], age, userInfo[6]);

        try {
            tempUser.isValidUser();
        }catch (InvalidUserInformations e) {
            System.out.println(e.getMessage());
            return null;
        }

        return tempUser;
    }

}
