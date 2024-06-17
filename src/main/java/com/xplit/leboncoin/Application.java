package com.xplit.leboncoin;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.service.AdService;
import com.xplit.leboncoin.service.UserService;
import com.xplit.leboncoin.util.DataInitializer;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
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
                    input = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException ignored) {
                    System.out.println("\nVeuillez entrer un nombre valide");
                }

                switch (input) {
                    case 1 -> UserService.showUsers(users);
                    case 2 -> AdService.showAds(ads);
                    case 3 -> UserService.createUser(users);
                    case 4 -> AdService.createAd(users, ads);
                    case 5 -> UserService.updateUser(users, ads);
                    case 6 -> AdService.updateAd(users, ads);
                    case 7 -> UserService.deleteUser(users, ads);
                    case 8 -> AdService.deleteAd(ads);
                    case 9 -> {
                        System.out.println("\nAu revoir !");
                        scanner.close();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Application.java unknown error " + e.getMessage());
        }
    }

}