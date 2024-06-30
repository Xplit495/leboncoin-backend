package com.xplit.leboncoin.authorization;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.service.AdService;
import com.xplit.leboncoin.service.UserService;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.List;
import java.util.Scanner;

public class AdminMode {
    public static void runAdmin(Scanner scanner, List<User> users, List<Ad> ads) {
        while (true) {
            int input;
            System.out.print("""

                    1. Voir les utilisateurs\

                    2. Voir les annonces\

                    3. Créer un utilisateur\

                    4. Créer une annonce\

                    5. Modifier un utilisateur\

                    6. Modifier une annonce\

                    7. Supprimer un utilisateur\

                    8. Supprimer une annonce\

                    9. Se déconnecter\


                    Votre choix :\s""");

            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= 1 && input <= 9) {
                    switch (input) {
                        case 1 -> UserService.showUsers(users);
                        case 2 -> AdService.showAds(users);
                        case 3 -> UserService.createUser(scanner, users);
                        case 4 -> AdService.adminCreateAd(scanner, users);
                        case 5 -> UserService.updateUser(scanner, users, ads);
                        case 6 -> AdService.adminUpdateAd(scanner, users);
                        case 7 -> UserService.deleteUser(scanner, users, ads);
                        case 8 -> AdService.adminDeleteAd(scanner, users);
                        case 9 -> {
                            System.out.println(TerminalColor.YELLOW + "\nDéconnexion..." + TerminalColor.RESET);
                            return;
                        }
                    }
                } else {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entre 1 et 9" + TerminalColor.RESET);
                }

            } catch (NumberFormatException ignored) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre" + TerminalColor.RESET);
            }
        }
    }

}
