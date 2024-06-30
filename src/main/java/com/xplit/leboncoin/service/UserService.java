package com.xplit.leboncoin.service;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidUserInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserService {

    public static void assignAdsToUser(List<User> users, List<Ad> ads) {
        List<Ad> remainingAds = new ArrayList<>(ads);
        for (User user : users) {
            List<Ad> assignedAds = new ArrayList<>();
            for (Ad ad : remainingAds) {
                if (ad.getOwner().equals(user.getId())) {
                    user.addAd(ad);
                    assignedAds.add(ad);
                }
            }

            remainingAds.removeAll(assignedAds);
        }
    }

    public static void showUsers(List<User> users) {
        for (int i = 0; i < 40; i++) {
            System.out.println('\n');
        }

        System.out.println(TerminalColor.YELLOW + "\nListe des utilisateurs :" + TerminalColor.RESET);
        for (User user : users) {
            System.out.println("\n====================================================================================");
            System.out.println(user);
            System.out.print("====================================================================================\n\n");
        }
    }

    public static void createUser(Scanner scanner, List<User> users) {
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

    public static void adminUpdateProfile(Scanner scanner, List<User> users) {
        String prompt = "Quel utilisateur souhaitez-vous modifier ? : ";
        int index = listAndSelectUser(scanner, users, prompt);
        User selectedUser = users.get(index);
        User userCopy = new User(selectedUser);

        updateProfileInternal(scanner, users, selectedUser, userCopy, index, true);
    }

    public static void userUpdateProfile(Scanner scanner, User selectedUser) {
        User userCopy = new User(selectedUser);

        updateProfileInternal(scanner, null, selectedUser, userCopy, null, false);
    }

    private static void updateProfileInternal(Scanner scanner, List<User> users, User selectedUser, User userCopy, Integer index, boolean isAdmin) {
        while (true) {
            System.out.println(TerminalColor.YELLOW + "\nL'utilisateur est :\n" + TerminalColor.RESET + selectedUser);
            if (isAdmin) {
                printAdminMenu();
            } else {
                printUserMenu();
            }

            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == (isAdmin ? 9 : 8)) {
                    System.out.println(TerminalColor.YELLOW + "\nSortie de la modification" + TerminalColor.RESET);
                    return;
                }

                if ((isAdmin && input >= 1 && input <= 8) || (!isAdmin && input >= 1 && input <= 7)) {
                    if (isAdmin && input == 1) {
                        boolean isModify = newId(scanner, selectedUser);
                        if (!isModify) {
                            System.out.println(TerminalColor.YELLOW + "\nAucun changement appliqué" + TerminalColor.RESET);
                        } else {
                            System.out.println(TerminalColor.GREEN + "\nUtilisateur modifié avec succès" + TerminalColor.RESET);
                        }
                    } else {
                        processInput(input, userCopy, scanner, isAdmin);

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

    private static void printAdminMenu() {
        System.out.print("\n\n" + """
                Quelle information souhaitez-vous modifier ?\

                1. ID\

                2. Prénom\

                3. Nom\

                4. Pseudo\

                5. Mail\

                6. Téléphone\

                7. Âge\

                8. Région\

                9. Quitter\
                                

                Votre choix :\s""");
    }

    private static void printUserMenu() {
        System.out.print("\n\n" + """
                Quelle information souhaitez-vous modifier ?\

                1. Prénom\

                2. Nom\

                3. Pseudo\

                4. Mail\

                5. Téléphone\

                6. Âge\

                7. Région\

                8. Quitter\


                Votre choix :\s""");
    }

    private static boolean newId(Scanner scanner, User selectedUser) {
        System.out.println("\nPour des raisons de sécurité, l'ID ne peut pas être modifié.\nVoulez-vous en générer un nouveau à la place ?\n1. Oui\n2. Non");
        while (true) {
            System.out.print("Votre choix : ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    if (choice == 1) {
                        UUID newId = UUID.randomUUID();
                        selectedUser.setId(newId);

                        for (Ad ad : selectedUser.getAds()) {
                            ad.setOwner(newId);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    System.out.println(TerminalColor.RED + "Veuillez entrer 1 ou 2" + TerminalColor.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "Veuillez entrer un nombre entier" + TerminalColor.RESET);
            }
        }
    }

    private static void processInput(int input, User userCopy, Scanner scanner, boolean isAdmin) {
        if (isAdmin && input == 1) {
            return;
        }
        int actualInput = isAdmin ? input : input + 1;
        switch (actualInput) {
            case 2 -> userCopy.setFirstName(getInput(scanner, "\nNouveau prénom : "));
            case 3 -> userCopy.setLastName(getInput(scanner, "\nNouveau nom : "));
            case 4 -> userCopy.setUsername(getInput(scanner, "\nNouveau pseudo : "));
            case 5 -> userCopy.setMail(getInput(scanner, "\nNouveau mail : "));
            case 6 -> userCopy.setPhone(getInput(scanner, "\nNouveau téléphone : "));
            case 7 -> userCopy.setAge(getValidAge(scanner));
            case 8 -> {
                userCopy.setRegion(getInput(scanner, "\nNouvelle région : "));
                for (Ad ad : userCopy.getAds()) {
                    ad.setRegion(userCopy.getRegion());
                }
            }
        }
    }

    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static Integer getValidAge(Scanner scanner) {
        while (true) {
            System.out.print("\nNouvel âge : ");
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) return null;
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "Veuillez entrer un nombre entier." + TerminalColor.RESET);
            }
        }
    }

    public static void userDeleteAccount(Scanner scanner, List<User> users, int index) {
        deleteUserInternal(scanner, users, index); // This is useless, but it's here to render the code more readable
    }

    public static void adminDeleteAccount(Scanner scanner, List<User> users) {
        String prompt = "Quel utilisateur souhaitez-vous supprimer ? : ";
        int index = listAndSelectUser(scanner, users, prompt);

        deleteUserInternal(scanner, users, index);
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

    public static int listAndSelectUser(Scanner scanner, List<User> users, String prompt) {
        boolean repetition = true;

        while (true) {
            if (repetition) {
                repetition = false;
                System.out.println(TerminalColor.YELLOW + "\nListe des utilisateurs :" + TerminalColor.RESET);
                for (int i = 0; i < users.size(); i++) {
                    System.out.println("\n" + TerminalColor.YELLOW + (i + 1) + "." + TerminalColor.RESET);
                    System.out.print("========================================");
                    System.out.println(users.get(i).shortToString());
                    System.out.print("========================================\n");
                }
            }

            System.out.print('\n' + prompt);
            String input = scanner.nextLine();

            try {
                int userIndex = Integer.parseInt(input);
                if (userIndex >= 1 && userIndex <= users.size()) {
                    return userIndex - 1;
                } else {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre de la liste des utilisateurs" + TerminalColor.RESET);
                }
            } catch (NumberFormatException ignored) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entier" + TerminalColor.RESET);
            }
        }
    }

}
