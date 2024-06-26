package com.xplit.leboncoin.authorization;

import com.xplit.leboncoin.internal.internalAd.UtilAd;
import com.xplit.leboncoin.internal.internalUser.InternalUser;
import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.service.admin.AdminAdService;
import com.xplit.leboncoin.service.admin.AdminUserService;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.List;
import java.util.Scanner;

/**
 * The AdminMode class provides the functionality for running the application in admin mode.
 * It allows the admin to view and manage users and ads, create new users and ads, update user profiles and ads, and delete accounts and ads.
 *
 * @version 1.0
 */
public class AdminMode {

    /**
     * Runs the application in admin mode, allowing the admin to interact with the system.
     *
     * @param scanner the Scanner object for admin input
     * @param users the list of users
     */
    public static void runAdmin(Scanner scanner, List<User> users) {
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur n'a été trouvé");
            return;
        }

        while (true) {
            int input;
            System.out.print("""

                    1. Voir les utilisateurs
                    2. Voir les annonces
                    3. Créer un utilisateur
                    4. Créer une annonce
                    5. Modifier un profil
                    6. Modifier une annonce
                    7. Supprimer un compte
                    8. Supprimer une annonce
                    9. Se déconnecter

                    Votre choix :\s""");

            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= 1 && input <= 9) {
                    switch (input) {
                        case 1 -> UtilUser.showUsers(users);
                        case 2 -> UtilAd.showAds(users);
                        case 3 -> InternalUser.createUserInternal(scanner, users);
                        case 4 -> AdminAdService.adminCreateAd(scanner, users);
                        case 5 -> AdminUserService.adminUpdateProfile(scanner, users);
                        case 6 -> AdminAdService.adminUpdateAd(scanner, users);
                        case 7 -> AdminUserService.adminDeleteAccount(scanner, users);
                        case 8 -> AdminAdService.adminDeleteAd(scanner, users);
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
