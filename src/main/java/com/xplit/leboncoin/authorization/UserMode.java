package com.xplit.leboncoin.authorization;

import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.internal.internalAd.UtilAd;
import com.xplit.leboncoin.service.user.UserAdService;
import com.xplit.leboncoin.service.user.UserService;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.List;
import java.util.Scanner;

public class UserMode {
    public static void runUser(Scanner scanner, List<User> users) {
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur n'a été trouvé");
            return;
        }

        String prompt = "Avec quel utilisateur souhaitez-vous vous connecter : ";
        int index = UtilUser.fetchAndChooseUser(scanner, users, prompt);

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
                        case 1 -> UtilAd.showAds(users);
                        case 2 -> selectedUser.showSelectedUserAds();
                        case 3 -> UserAdService.userCreateAd(scanner, selectedUser);
                        case 4 -> UserAdService.userUpdateAd(scanner, selectedUser);
                        case 5 -> UserAdService.userDeleteAd(scanner, selectedUser);
                        case 6 -> UserService.userUpdateProfile(scanner, selectedUser);
                        case 7 -> {
                            UserService.userDeleteAccount(scanner, users, index);
                            System.out.println(TerminalColor.YELLOW + "\nDéconnexion..." + TerminalColor.RESET);
                            return;
                        }
                        case 8 -> {
                            System.out.println(TerminalColor.YELLOW + "\nDéconnexion..." + TerminalColor.RESET);
                            return;
                        }
                    }
                } else {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entre 1 et 8" + TerminalColor.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre valide" + TerminalColor.RESET);
            }
        }

    }

}
