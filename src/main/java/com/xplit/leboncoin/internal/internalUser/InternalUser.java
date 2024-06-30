package com.xplit.leboncoin.internal.internalUser;

import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidUserInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The InternalUser class provides internal methods for managing users,
 * including creating, updating, and deleting user profiles.
 *
 * @version 1.0
 */
public class InternalUser {

    /**
     * Creates a new user based on input from the scanner and adds it to the list of users.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users to add the new user to
     */
    public static void createUserInternal(Scanner scanner, List<User> users) {
        Integer age = null;
        String[] questions = {"Prénom (Obligatoire)", "Nom (Facultatif)", "Pseudo (Obligatoire)",
                "Mail (Obligatoire)", "Téléphone (Facultatif)", "Âge (Facultatif)", "Région (Obligatoire)"};
        String[] userInfos = new String[7];

        // Collect user information from input
        for (int i = 0; i < questions.length; i++) {
            System.out.print(questions[i] + " : ");
            String input = scanner.nextLine();
            userInfos[i] = input;
        }

        // Parse age if possible
        try {
            age = Integer.parseInt(userInfos[5]);
        } catch (NumberFormatException ignored) {
        }

        // Create a temporary user with the collected information
        User tempUser = new User(userInfos[0], userInfos[1], userInfos[2],
                userInfos[3], userInfos[4], age, userInfos[6], new ArrayList<>());

        // Validate the temporary user
        try {
            tempUser.isValidUser();
        } catch (InvalidUserInformations e) {
            System.out.print(TerminalColor.RED + e.getMessage() + TerminalColor.RESET);
            tempUser = null;
        }

        // Add the user to the list if valid
        if (tempUser != null) {
            System.out.println(TerminalColor.GREEN + "\nUtilisateur créé avec succès" + TerminalColor.RESET);
            users.add(tempUser);
        }
    }

    /**
     * Updates the profile of a selected user based on input from the scanner.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users
     * @param selectedUser the user to update
     * @param userCopy a copy of the user to update
     * @param index the index of the user in the list
     * @param isAdmin true if the update is performed by an admin, false otherwise
     */
    public static void updateProfileInternal(Scanner scanner, List<User> users, User selectedUser, User userCopy, Integer index, boolean isAdmin) {
        while (true) {
            System.out.println(TerminalColor.YELLOW + "\nL'utilisateur est :\n" + TerminalColor.RESET + userCopy);
            if (isAdmin) {
                UtilUser.printAdminMenu();
            } else {
                UtilUser.printUserMenu();
            }

            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == (isAdmin ? 9 : 8)) {
                    System.out.println(TerminalColor.YELLOW + "\nSortie de la modification" + TerminalColor.RESET);
                    return;
                }

                if ((isAdmin && input >= 1 && input <= 8) || (!isAdmin && input >= 1 && input <= 7)) {
                    if (isAdmin && input == 1) {
                        boolean isModify = UtilUser.newId(scanner, selectedUser);
                        if (!isModify) {
                            System.out.println(TerminalColor.YELLOW + "\nAucun changement appliqué" + TerminalColor.RESET);
                        } else {
                            System.out.println(TerminalColor.GREEN + "\nUtilisateur modifié avec succès" + TerminalColor.RESET);
                        }
                    } else {
                        UtilUser.processInput(input, userCopy, scanner, isAdmin);

                        try {
                            userCopy.isValidUser();
                            if (isAdmin) {
                                users.set(index, userCopy);
                            } else {
                                selectedUser.copyFrom(userCopy);
                            }
                            System.out.println(TerminalColor.GREEN + "\nUtilisateur modifié avec succès" + TerminalColor.RESET);
                        } catch (InvalidUserInformations e) {
                            System.out.println(TerminalColor.RED + e.getMessage() + "\n" + "Données invalides, aucun changement appliqué" + TerminalColor.RESET);
                        }
                    }
                } else {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre valide" + TerminalColor.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre valide." + TerminalColor.RESET);
            }
        }
    }

    /**
     * Deletes a user from the list of users based on input from the scanner.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users
     * @param index the index of the user to delete
     */
    public static void deleteUserInternal(Scanner scanner, List<User> users, int index) {
        System.out.println("\nVoulez-vous vraiment supprimer ce compte et toutes les annonces qui lui sont associée(s) ?\n1. Oui\n2. Non");

        while (true) {
            System.out.print("Votre choix : ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    if (choice == 1) {
                        users.remove(index);
                        System.out.println(TerminalColor.GREEN + "\nUtilisateur supprimé avec succès" + TerminalColor.RESET);
                    } else {
                        System.out.println(TerminalColor.RED + "\nSuppression annulée\n" + TerminalColor.RESET);
                    }
                    break;
                } else {
                    System.out.println(TerminalColor.RED + "Veuillez entrer 1 ou 2" + TerminalColor.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "Veuillez entrer un nombre entier" + TerminalColor.RESET);
            }
        }
    }
}
