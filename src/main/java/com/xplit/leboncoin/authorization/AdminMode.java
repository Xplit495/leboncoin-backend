package com.xplit.leboncoin.authorization;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.service.AdService;
import com.xplit.leboncoin.service.UserService;

import java.util.List;
import java.util.Scanner;

public class AdminMode {
    public void runAdmin(Scanner scanner, List<User> users, List<Ad> ads) {
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
                    
                    9. Retour au menu principal\
                    

                    Votre choix : \s""");

            try {
                // Read user input
                input = Integer.parseInt(scanner.nextLine());
                if (input >= 1 && input <= 9) {
                    // Handle user input and perform corresponding operations
                    switch (input) {
                        case 1 -> UserService.showUsers(users); // Show all users
                        case 2 -> AdService.showAds(ads); // Show all ads
                        case 3 -> UserService.createUser(scanner, users); // Create a new user
                        case 4 -> AdService.createAd(scanner, users, ads); // Create a new ad
                        case 5 -> UserService.updateUser(scanner, users, ads); // Update an existing user
                        case 6 -> AdService.updateAd(scanner, users, ads); // Update an existing ad
                        case 7 -> UserService.deleteUser(scanner, users, ads); // Delete a user
                        case 8 -> AdService.deleteAd(scanner, ads); // Delete an ad
                        case 9 -> {
                            System.out.println("\nSortie du mode administrateur");
                            return; // Exit the admin mode
                        }
                    }
                } else {
                    System.out.println("\nVeuillez entrer un nombre entre 1 et 9");
                }

            } catch (NumberFormatException ignored) {
                System.out.println("\nVeuillez entrer un nombre");
            }
        }
    }

}
