package com.xplit.leboncoin.service;

import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidUserInformations;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserService {

    public static void showUsers(List<User> users) {
        for (int i = 0; i < 40; i++) {System.out.println('\n');}
        System.out.println("\nListe des utilisateurs :");
        users.forEach(System.out::println);
    }

    public static void createUser(List<User> users) {
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
            tempUser = null;
        }

        if (tempUser != null) {
            System.out.println("\nUtilisateur créé avec succès");
            users.add(tempUser);
        }

    }

    public static void updateUser(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        String message = "Quel utilisateur souhaitez-vous modifier ? : ";
        int index = listAndSelectUser(users, message);
        User userToUpdate = users.get(index);

        System.out.println("\nVous avez sélectionné l'utilisateur suivant :\n\n" + userToUpdate +
                "\nQuelle information souhaitez-vous modifier ?" +
                "\n 1. Prénom" +
                "\n 2. Nom" +
                "\n 3. Nom d'utilisateur" +
                "\n 4. Âge" +
                "\n 5. Téléphone" +
                "\n 6. Région" +
                "\n 7. Mail" +
                "\n 8. Annuler\n");

    }

    public static int listAndSelectUser (List<User> users, String message) {
        Scanner scanner = new Scanner(System.in);

        int index;
        boolean repetition = true;

        while (true) {
            if (repetition) {
                System.out.println("\nListe des utilisateurs :\n\n");
                int count = 1;
                for (User user : users) {
                    System.out.printf("%d :\nID : %s\nPrénom : %s\nNom : %s\nPseudo : %s\nMail : %s\n\n",
                            count++, user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getMail());
                }
            }
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                index = Integer.parseInt(input) - 1;
                index = index >= 0 && index < users.size() ? index : 0;
                if (index != 0) {
                    break;
                } else {
                    System.out.println("\nVeuillez entrer un nombre entier de la liste");
                    repetition = false;
                }
            } catch (NumberFormatException ignored) {
                System.out.println("\nVeuillez entrer un nombre entier");
                repetition = false;
            }
        }
        return index;
    }

}
