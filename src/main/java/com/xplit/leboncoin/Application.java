package com.xplit.leboncoin;

import com.xplit.leboncoin.authorization.AdminMode;
import com.xplit.leboncoin.authorization.UserMode;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.DataInitializer;

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
            // Initialize users and ads from JSON files
            List<User> users = DataInitializer.initializeUsers("src/main/resources/users.json");
            List<Ad> ads = DataInitializer.initializeAds("src/main/resources/ads.json", users);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                int input;
                System.out.print("""
                        
                        1. Mode administrateur\
                        
                        2. Mode utilisateur\
                        
                        3. Quitter\
                        

                        Votre choix : \s""");

                try {
                    // Read user input
                    input = Integer.parseInt(scanner.nextLine());
                        // Handle user input and perform corresponding operations
                        if (input == 1) {
                            AdminMode adminMode = new AdminMode();
                            adminMode.runAdmin(scanner, users, ads);
                        } else if (input == 2) {
                            UserMode userMode = new UserMode();
                            userMode.runUser(scanner, users, ads);
                        } else if (input == 3){
                            System.out.println("\nAu revoir !");
                            scanner.close();
                            return;
                        } else {
                            System.out.println("\nVeuillez entrer un nombre entre 1 et 3");
                        }
                } catch (NumberFormatException ignored) {
                    System.out.println("\nVeuillez entrer un nombre");
                }
            }
        } catch (Exception e) {
            System.out.println("Application.java unknown error " + e.getMessage());
        }
    }

}
