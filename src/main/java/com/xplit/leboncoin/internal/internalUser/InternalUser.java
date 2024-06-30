package com.xplit.leboncoin.internal.internalUser;

import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidUserInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InternalUser {

    public static void createUserInternal(Scanner scanner, List<User> users) {
        Integer age = null;
        String[] questions = {"Prénom (Obligatoire)", "Nom (Facultatif)", "Pseudo (Obligatoire)",
                "Mail (Obligatoire)", "Téléphone (Facultatif)", "Âge (Facultatif)", "Région (Obligatoire)"};
        String[] userInfos = new String[7];

        for (int i = 0; i < questions.length; i++) {
            System.out.print(questions[i] + " : ");
            String input = scanner.nextLine();
            userInfos[i] = input;
        }

        try {
            age = Integer.parseInt(userInfos[5]);
        } catch (NumberFormatException ignored) {
        }

        User tempUser = new User(userInfos[0], userInfos[1], userInfos[2],
                userInfos[3], userInfos[4], age, userInfos[6], new ArrayList<>());

        try {
            tempUser.isValidUser();
        } catch (InvalidUserInformations e) {
            System.out.println(TerminalColor.RED + e.getMessage() + TerminalColor.RESET);
            tempUser = null;
        }

        if (tempUser != null) {
            System.out.println(TerminalColor.GREEN + "\nUtilisateur créé avec succès" + TerminalColor.RESET);
            users.add(tempUser);
        }
    }

    public static void updateProfileInternal(Scanner scanner, List<User> users, User selectedUser, User userCopy, Integer index, boolean isAdmin) {
        while (true) {
            System.out.println(TerminalColor.YELLOW + "\nL'utilisateur est :\n" + TerminalColor.RESET + selectedUser);
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
