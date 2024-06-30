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
 * The Application class is the entry point for the Leboncoin backend simulation.
 * It initializes data and handles user input to choose between user and admin modes.
 *
 * @version 1.0
 * @author Xplit495
 */
public class Application {

    /**
     * The main method to run the application.
     * It initializes the users and ads, assigns ads to users, and provides
     * a menu for selecting admin mode, user mode, or quitting the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        try {
            // Initialize users and ads from JSON files
            List<User> users = DataInitializer.initializeUsers("src/main/resources/users.json");
            List<Ad> ads = DataInitializer.initializeAds("src/main/resources/ads.json", users);

            // Assign ads to users
            UtilUser.assignAdsToUser(users, ads);

            // Create a scanner object to read user input
            Scanner scanner = new Scanner(System.in);

            // Main application loop
            while (true) {
                int input;

                // Display menu options
                System.out.print("""

                        1. Mode administrateur
                        2. Mode utilisateur
                        3. Quitter

                        Votre choix :\s""");

                try {
                    // Read user input
                    input = Integer.parseInt(scanner.nextLine());

                    // Handle different user choices
                    if (input == 1) {
                        // Run admin mode
                        AdminMode.runAdmin(scanner, users);
                    } else if (input == 2) {
                        // Run user mode
                        UserMode.runUser(scanner, users);
                    } else if (input == 3) {
                        // Exit the application
                        System.out.println(TerminalColor.YELLOW + "\nAu revoir !" + TerminalColor.RESET);
                        scanner.close();
                        return;
                    } else {
                        // Invalid input, prompt the user to enter a number between 1 and 3
                        System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entre 1 et 3" + TerminalColor.RESET);
                    }
                } catch (NumberFormatException ignored) {
                    // Handle invalid input format
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre" + TerminalColor.RESET);
                }
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions
            System.out.println(TerminalColor.RED + "Application.java unknown error " + e.getMessage() + TerminalColor.RESET);
        }
    }
}
