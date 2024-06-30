package com.xplit.leboncoin.authorization;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.service.AdService;
import com.xplit.leboncoin.service.UserService;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.List;
import java.util.Scanner;

public class UserMode {
    public static void runUser(Scanner scanner, List<User> users, List<Ad> ads) {
        String prompt = "Avec quel utilisateur souhaitez-vous vous connecter : ";
        int index = UserService.listAndSelectUser(scanner, users, prompt);

        User selectedUser = users.get(index);

        System.out.print(TerminalColor.YELLOW + "\n\nVous êtes connecté en tant que :\n" + TerminalColor.RESET + selectedUser + "\n\n");

        while (true) {
            int input;
            System.out.print("""

                    1. Voir les annonces\

                    2. Voir vos annonces\

                    3. Créer une annonce\

                    4. Modifier une annonce\

                    5. Supprimer une annonce\

                    6. Modifier votre profil\

                    7. Supprimer votre compte\

                    8. Se déconnecter\


                    Votre choix :\s""");
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= 1 && input <= 8) {
                    switch (input) {
                        case 1 -> AdService.showAds(users);
                        case 2 -> AdService.showSelectedUserAds(selectedUser);
                        case 3 -> AdService.userCreateAd(scanner, selectedUser);
                        case 4 -> AdService.userUpdateAd(scanner, selectedUser);
                        case 5 -> AdService.userDeleteAd(scanner, selectedUser);
                        case 6 -> UserService.updateUser(scanner, selectedUser);
                        case 7 -> {
                            UserService.deleteUser(scanner, users, ads, selectedUser);
                            System.out.println(TerminalColor.YELLOW + "\nDéconnexion..." + TerminalColor.RESET);
                            return;
                        }
                        case 8 -> {
                            System.out.println(TerminalColor.YELLOW + "\nDéconnexion..." + TerminalColor.RESET);
                            return;
                        }
                    }
                } else {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entre 1 et 7" + TerminalColor.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre valide" + TerminalColor.RESET);
            }
        }

    }

}
