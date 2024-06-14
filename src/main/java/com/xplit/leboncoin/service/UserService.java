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
        String[] userInfos = new String[7];

        for (int i = 0; i < prompt.length; i++) {
            System.out.print(prompt[i] + " : ");
            String input = scanner.nextLine();

            if (input.isEmpty()){
                userInfos[i] = null;
            } else {
                userInfos[i] = input;
            }
        }

        try {
            age = Integer.parseInt(userInfos[5]);
        } catch (NumberFormatException ignored) {}

        User tempUser = new User (userInfos[0], userInfos[1], userInfos[2], userInfos[3], userInfos[4], age, userInfos[6]);

        try {
            tempUser.isValidUser();
        }catch (InvalidUserInformations e) {
            System.out.println(e.getMessage());
            return null;
        }

        System.out.println("\nUtilisateur créé avec succès");
        return tempUser;
    }

}
