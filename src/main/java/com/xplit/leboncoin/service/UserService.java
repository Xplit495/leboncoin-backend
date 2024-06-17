package com.xplit.leboncoin.service;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidUserInformations;

import java.util.List;
import java.util.Scanner;

/**
 * The UserService class provides methods to manage users.
 */
public class UserService {

    /**
     * Displays all users in the list.
     *
     * @param users List of users to display
     */
    public static void showUsers(List<User> users) {
        for (int i = 0; i < 40; i++) {
            System.out.println('\n');
        }
        System.out.println("\nListe des utilisateurs :");
        users.forEach(System.out::println);
    }

    /**
     * Creates a new user and adds it to the list of users.
     *
     * @param users List of users to add the new user to
     */
    public static void createUser(List<User> users) {
        Scanner scanner = new Scanner(System.in);

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
        } catch (NumberFormatException ignored) {}

        User tempUser = new User(userInfos[0], userInfos[1], userInfos[2], userInfos[3], userInfos[4], age, userInfos[6]);

        try {
            tempUser.isValidUser();
        } catch (InvalidUserInformations e) {
            System.out.println(e.getMessage());
            tempUser = null;
        }

        if (tempUser != null) {
            System.out.println("\nUtilisateur créé avec succès");
            users.add(tempUser);
        }
    }

    /**
     * Updates an existing user.
     *
     * @param users List of users to update
     * @param ads   List of advertisements associated with the users
     */
    public static void updateUser(List<User> users, List<Ad> ads) {
        Scanner scanner = new Scanner(System.in);
        String prompt = "Quel utilisateur souhaitez-vous modifier ? : ";
        int index = listAndSelectUser(users, prompt);

        while (true) {
            User originalUser = users.get(index);
            User userCopy = new User(originalUser);

            System.out.println("\nL'utilisateur sélectionné est :\n" + originalUser);
            printMenu();

            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == 9) {
                    System.out.println("\nSortie de la modification\n");
                    return;
                }

                if (input == 1) {
                    originalUser.newId(scanner, ads);
                    if (originalUser.getId().equals(userCopy.getId())) {
                        System.out.println("\nAucun changement appliqué");
                    } else {
                        System.out.println("\nUtilisateur modifié avec succès\n");
                    }
                } else if (input >= 2 && input <= 8) {
                    processInput(input, userCopy, scanner);

                    try {
                        userCopy.isValidUser();
                        users.set(index, userCopy);
                        System.out.println("\nUtilisateur modifié avec succès\n");

                    } catch (InvalidUserInformations e) {
                        System.out.println(e.getMessage() + "\n" + "Données invalides, aucun changement appliqué");
                    }
                } else {
                    System.out.println("Veuillez entrer un nombre entre 1 et 8");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    /**
     * Prints the menu for modifying a user.
     */
    private static void printMenu() {
        System.out.print("""
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

    /**
     * Processes the user input for updating the user.
     *
     * @param input    The user input
     * @param userCopy The user to update
     * @param scanner  The scanner for user input
     */
    private static void processInput(int input, User userCopy, Scanner scanner) {
        switch (input) {
            case 2 -> userCopy.setFirstName(getInput(scanner, "\nNouveau prénom : "));
            case 3 -> userCopy.setLastName(getInput(scanner, "\nNouveau nom : "));
            case 4 -> userCopy.setUsername(getInput(scanner, "\nNouveau pseudo : "));
            case 5 -> userCopy.setMail(getInput(scanner, "\nNouveau mail : "));
            case 6 -> userCopy.setPhone(getInput(scanner, "\nNouveau téléphone : "));
            case 7 -> userCopy.setAge(getValidAge(scanner));
            case 8 -> userCopy.setRegion(getInput(scanner, "\nNouvelle région : "));
        }
    }

    /**
     * Gets input from the user.
     *
     * @param scanner The scanner for user input
     * @param prompt  The prompt to display to the user
     * @return The user input
     */
    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Gets a valid age input from the user.
     *
     * @param scanner The scanner for user input
     * @return The valid age input
     */
    private static Integer getValidAge(Scanner scanner) {
        while (true) {
            System.out.print("\nNouvel âge : ");
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) return null;
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier.");
            }
        }
    }

    /**
     * Deletes a user and their ads from the list.
     *
     * @param users List of users to delete from
     * @param ads   List of advertisements to delete
     */
    public static void deleteUser(List<User> users, List<Ad> ads) {
        Scanner scanner = new Scanner(System.in);
        String prompt = "Quel utilisateur souhaitez-vous supprimer ? : ";
        int index = listAndSelectUser(users, prompt);

        User userToDelete = users.get(index);
        System.out.println("\nL'utilisateur sélectionné est :\n" + userToDelete);

        System.out.println("Voulez-vous vraiment supprimer cet utilisateur et toutes ses annonces ?\n1. Oui\n2. Non");
        while (true) {
            System.out.print("Votre choix : ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    if (choice == 1) {
                        users.remove(index);
                        ads.removeIf(ad -> ad.getOwner().equals(userToDelete.getId()));
                        System.out.println("\nUtilisateur supprimé avec succès\n");
                    } else {
                        System.out.println("\nSuppression annulée\n");
                    }
                    break;
                } else {
                    System.out.println("Veuillez entrer 1 ou 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier");
            }
        }
    }

    /**
     * Lists and selects a user from the list.
     *
     * @param users  List of users to select from
     * @param prompt The prompt to display to the user
     * @return The index of the selected user
     */
    public static int listAndSelectUser(List<User> users, String prompt) {
        Scanner scanner = new Scanner(System.in);

        int userIndex;
        boolean repetition = true;

        while (true) {
            if (repetition) {
                repetition = false;
                System.out.println("\nListe des utilisateurs :");
                for (int i = 0; i < users.size(); i++) {
                    System.out.println("\n" + (i + 1) + ".");
                    System.out.print("========================================");
                    System.out.println(users.get(i).shortToString());
                    System.out.print("========================================\n");
                }
            }

            System.out.print('\n' + prompt);
            String input = scanner.nextLine();
            try {
                userIndex = Integer.parseInt(input) - 1;
                userIndex = userIndex >= 0 && userIndex < users.size() ? userIndex : 0;
                if (userIndex != 0) {
                    break;
                } else {
                    System.out.println("\nVeuillez entrer un nombre de la liste des utilisateurs");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("\nVeuillez entrer un nombre entier");
            }
        }
        return userIndex;
    }

}
