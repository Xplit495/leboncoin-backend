package com.xplit.leboncoin.internal.internalUser;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * The UtilUser class provides utility methods for managing users and their ads,
 * including assigning ads to users, displaying user information, and processing user input.
 *
 * @version 1.0
 */
public class UtilUser {

    /**
     * Assigns ads to users based on the owner ID.
     *
     * @param users the list of users
     * @param ads the list of ads
     */
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

    /**
     * Prompts the user to select a user from a list and returns the selected user's index.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users
     * @param prompt the prompt to display to the user
     * @return the index of the selected user
     */
    public static int fetchAndChooseUser(Scanner scanner, List<User> users, String prompt) {
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

    /**
     * Displays a list of all users.
     *
     * @param users the list of users
     */
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

    /**
     * Prints the admin menu for modifying user information.
     */
    public static void printAdminMenu() {
        System.out.print("\n\n" + """
                Quelle information souhaitez-vous modifier ?
                1. ID
                2. Prénom
                3. Nom
                4. Pseudo
                5. Mail
                6. Téléphone
                7. Âge
                8. Région
                9. Quitter

                Votre choix :\s""");
    }

    /**
     * Prints the user menu for modifying their own information.
     */
    public static void printUserMenu() {
        System.out.print("\n\n" + """
                Quelle information souhaitez-vous modifier ?
                1. Prénom
                2. Nom
                3. Pseudo
                4. Mail
                5. Téléphone
                6. Âge
                7. Région
                8. Quitter

                Votre choix :\s""");
    }

    /**
     * Prompts the user to decide if they want to generate a new ID.
     * If the user chooses to generate a new ID, it updates the user's ID and their ads' owner ID.
     *
     * @param scanner the Scanner object for user input
     * @param selectedUser the user to update the ID for
     * @return true if a new ID was generated, false otherwise
     */
    public static boolean newId(Scanner scanner, User selectedUser) {
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

    /**
     * Processes the user's input for updating their profile information.
     *
     * @param input the input choice
     * @param userCopy the user object to update
     * @param scanner the Scanner object for user input
     * @param isAdmin true if the operation is performed by an admin, false otherwise
     */
    public static void processInput(int input, User userCopy, Scanner scanner, boolean isAdmin) {
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

    /**
     * Prompts the user for input with the specified prompt.
     *
     * @param scanner the Scanner object for user input
     * @param prompt the prompt to display to the user
     * @return the user's input
     */
    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts the user for a valid age input.
     *
     * @param scanner the Scanner object for user input
     * @return the valid age input
     */
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
}
