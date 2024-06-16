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

    public static void createUser(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        Integer age = null;

        String[] prompt = {"Prénom (Obligatoire)", "Nom (Facultatif)", "Pseudo (Obligatoire)",
                "Mail (Obligatoire)", "Téléphone (Facultatif)", "Âge (Facultatif)" ,"Région (Obligatoire)"};
        String[] userInfos = new String[7];

        for (int i = 0; i < prompt.length; i++) {
            System.out.print(prompt[i] + " : ");
            String input = scanner.nextLine();
            userInfos[i] = input;
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

        while (true) {
            User originalUser = users.get(index);
            User userCopy = new User(originalUser);

            System.out.println("\nL'utilisateur sélectionné est :\n" + originalUser);
            printMenu();

            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == 8) {
                    System.out.println("\nSortie de la modification\n");
                    return;
                }

                if (input >= 1 && input <= 7) {
                    processInput(input, userCopy, scanner);

                    try {
                        userCopy.isValidUser();
                        users.set(index, userCopy);
                        System.out.println("\nUtilisateur modifié avec succès\n " + userCopy);

                        if (!askToContinue(scanner)) return;
                    } catch (InvalidUserInformations e){
                        System.out.println(e.getMessage() + "\n" + "Données invalides, aucun changement appliqué" );
                    }
                } else {
                    System.out.println("Veuillez entrer un nombre entre 1 et 8");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    private static void printMenu() {
        System.out.print("""
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

    private static void processInput(int input, User userCopy, Scanner scanner) {
        switch (input) {
            case 1 -> userCopy.setFirstName(getInput(scanner, "\nNouveau prénom : "));
            case 2 -> userCopy.setLastName(getInput(scanner, "\nNouveau nom : "));
            case 3 -> userCopy.setUsername(getInput(scanner, "\nNouveau pseudo : "));
            case 4 -> userCopy.setMail(getInput(scanner, "\nNouveau mail : "));
            case 5 -> userCopy.setPhone(getInput(scanner, "\nNouveau téléphone : "));
            case 6 -> userCopy.setAge(getValidInteger(scanner));
            case 7 -> userCopy.setRegion(getInput(scanner, "\nNouvelle région : "));
        }
    }

    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static Integer getValidInteger(Scanner scanner) {
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

    private static boolean askToContinue(Scanner scanner) {
        System.out.print("Voulez-vous modifier une autre information ?\n1. Oui\n2. Non\n\nVotre choix : ");
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                return choice == 1;
            } catch (NumberFormatException e) {
                System.out.print("\nVeuillez saisir un nombre entier.\nVotre choix : ");
            }
        }
    }

    public static int listAndSelectUser (List<User> users, String message) {
        Scanner scanner = new Scanner(System.in);

        int index;
        boolean repetition = true;

        while (true) {
            if (repetition) {
                System.out.println("\nListe des utilisateurs :\n");
                int count = 1;
                for (User user : users) {
                    System.out.printf("%d.\nID : %s\nPrénom : %s\nNom : %s\nPseudo : %s\nMail : %s\n\n",
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
