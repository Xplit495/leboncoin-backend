package com.xplit.leboncoin.service;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidAdInformations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AdService {

    public static void showAds(List<Ad> ads) {
        for (int i = 0; i < 40; i++) {System.out.println('\n');}
        System.out.println("\nListe des annonces :");
        ads.forEach(System.out::println);
    }

    public static Ad createAd(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        UUID publishBy;
        User currentUser;

        boolean repetition = true;
        while (true) {
            if (repetition) {
                System.out.println("\nListe des utilisateurs :\n\n");
                for (int i = 0; i < users.size(); i++) {
                    System.out.println(i + 1 + " :" + "\n"
                            + "ID : " + users.get(i).getId() + "\n"
                            + "Prénom : " + users.get(i).getFirstName() + "\n"
                            + "Nom : " + users.get(i).getLastName() + "\n"
                            + "Pseudo : " + users.get(i).getUsername() + "\n"
                            + "Mail : " + users.get(i).getMail() + "\n\n");
                }
            }
            System.out.print("À quel utilisateur souhaitez vous attribuer cette annonce ? : ");
            String input = scanner.nextLine();
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < users.size()) {
                    publishBy = users.get(index).getId();
                    currentUser = users.get(index);
                    break;
                } else {
                    System.out.println("\nVeuillez entrer un nombre entier de la liste");
                    repetition = false;
                }
            } catch (NumberFormatException ignored) {
                System.out.println("\nVeuillez entrer un nombre entier");
                repetition = false;
            }
        }

        Integer adPrice = null;
        String[] prompt = {"Titre (Obligatoire)", "Description (Obligatoire)", "Prix (Sans €) (Obligatoire)",
                "Catégorie (Obligatoire)"};
        String[] adInfos = new String[5];

        for (int j = 0; j < prompt.length; j++) {
            if (j == 3) {
                System.out.println("\nListe des catégories :\n" + Arrays.toString(Ad.categories));
            }

            System.out.print(prompt[j] + " : ");
            String input = scanner.nextLine();

            if (input.isEmpty()){
                adInfos[j] = null;
            } else {
                adInfos[j] = input;
            }
        }

        try {
            adPrice = Integer.parseInt(adInfos[2]);
        } catch (NumberFormatException ignored) {}

        String[] photos;

        while (true){
            System.out.print("Combien de photos souhaitez-vous ajouter à votre annonce ? (1 Min / 5 Max) : ");
            String input = scanner.nextLine();
            try {
                int photoCount = Integer.parseInt(input);
                if (photoCount > 0 && photoCount <= 5) {
                    photos = new String[photoCount];
                    for (int i = 0; i < photoCount; i++) {
                        System.out.print("Lien vers la photo " + (i + 1) + " : ");
                        photos[i] = scanner.nextLine();
                    }
                    break;
                } else {
                    System.out.println("\nVeuillez entrer un nombre compris entre 1 et 5");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("\nVeuillez entrer un nombre valide");
            }
        }

        Ad tempAd = new Ad (publishBy, adInfos[0], adInfos[1], photos, adPrice, currentUser.getRegion(), adInfos[3] , LocalDate.now().toString());

        try {
            tempAd.isValidAd();
        }catch (InvalidAdInformations e) {
            System.out.println(e.getMessage());
            return null;
        }

        System.out.println("\nAnnonce créé avec succès");
        return tempAd;
    }

}
