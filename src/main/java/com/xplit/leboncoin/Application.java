package com.xplit.leboncoin;

import com.xplit.leboncoin.authorization.AdminMode;
import com.xplit.leboncoin.authorization.UserMode;
import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.DataInitializer;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.List;
import java.util.Scanner;

/**
 * @author Xplit495
 * @version 1.0
 * The Application class is the entry point for the Leboncoin backend simulation.
 * It initializes data and handles user input to choose between user and admin modes.
 */
public class Application {

    /**
     * The main method to run the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        try {
            List<User> users = DataInitializer.initializeUsers("src/main/resources/users.json");
            List<Ad> ads = DataInitializer.initializeAds("src/main/resources/ads.json", users);
            UtilUser.assignAdsToUser(users, ads);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                int input;
                System.out.print("""

                        1. Mode administrateur\

                        2. Mode utilisateur\

                        3. Quitter\


                        Votre choix :\s""");

                try {
                    input = Integer.parseInt(scanner.nextLine());
                    if (input == 1) {
                        AdminMode.runAdmin(scanner, users);
                    } else if (input == 2) {
                        UserMode.runUser(scanner, users);
                    } else if (input == 3) {
                        System.out.println(TerminalColor.YELLOW + "\nAu revoir !" + TerminalColor.RESET);
                        scanner.close();
                        return;
                    } else {
                        System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entre 1 et 3" + TerminalColor.RESET);
                    }
                } catch (NumberFormatException ignored) {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre" + TerminalColor.RESET);
                }
            }
        } catch (Exception e) {
            System.out.println(TerminalColor.RED + "Application.java unknown error " + e.getMessage() + TerminalColor.RESET);
        }
    }

}
