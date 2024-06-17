package com.xplit.leboncoin;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.service.AdService;
import com.xplit.leboncoin.service.UserService;
import com.xplit.leboncoin.util.DataInitializer;

import java.util.List;
import java.util.Scanner;

/**
 * The Application class is the entry point for the Leboncoin backend simulation.
 * It initializes data and handles user input to perform various operations such as
 * viewing, creating, updating, and deleting users and ads.
 */
public class Application {

    /**
     * The main method to run the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Initialize users and ads from JSON files
            List<User> users = DataInitializer.initializeUsers("src/main/resources/users.json");
            List<Ad> ads = DataInitializer.initializeAds("src/main/resources/ads.json", users);

            while (true) {
                int input = 0;
                System.out.print("""
                    
                    1. Voir les utilisateurs\
                    
                    2. Voir les annonces\
                    
                    3. Créer un utilisateur\
                    
                    4. Créer une annonce\
                    
                    5. Modifier un utilisateur\
                    
                    6. Modifier une annonce\
                    
                    7. Supprimer un utilisateur\
                    
                    8. Supprimer une annonce\
                    
                    9. Quitter\
                    

                    Votre choix :\s""");

                try {
                    // Read user input
                    input = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException ignored) {
                    System.out.println("\nVeuillez entrer un nombre valide");
                }

                // Handle user input and perform corresponding operations
                switch (input) {
                    case 1 -> UserService.showUsers(users); // Show all users
                    case 2 -> AdService.showAds(ads); // Show all ads
                    case 3 -> UserService.createUser(users); // Create a new user
                    case 4 -> AdService.createAd(users, ads); // Create a new ad
                    case 5 -> UserService.updateUser(users, ads); // Update an existing user
                    case 6 -> AdService.updateAd(users, ads); // Update an existing ad
                    case 7 -> UserService.deleteUser(users, ads); // Delete a user
                    case 8 -> AdService.deleteAd(ads); // Delete an ad
                    case 9 -> {
                        System.out.println("\nAu revoir !");
                        scanner.close(); // Close the scanner
                        return; // Exit the application
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Application.java unknown error " + e.getMessage());
        }
    }

}
