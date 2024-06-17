package com.xplit.leboncoin.service;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidAdInformations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static com.xplit.leboncoin.service.UserService.listAndSelectUser;

public class AdService {

    public static void showAds(List<Ad> ads) {
        for (int i = 0; i < 40; i++) {System.out.println('\n');}
        System.out.println("\nListe des annonces :");
        ads.forEach(System.out::println);
    }

    public static void createAd(List<User> users, List<Ad> ads) {
        Scanner scanner = new Scanner(System.in);

        String prompt = "À quel utilisateur souhaitez vous attribuer cette annonce ? : ";
        int index = listAndSelectUser(users, prompt);
        UUID owner = users.get(index).getId();
        User currentUser = users.get(index);

        Integer adPrice = null;
        String[] questions = {"Titre (Obligatoire)", "Description (Obligatoire)", "Prix (Sans €) (Obligatoire)",
                "Catégorie (Obligatoire)"};
        String[] adInfos = new String[5];

        for (int j = 0; j < questions.length; j++) {
            if (j == 3) {
                System.out.println("\nListe des catégories :\n" + Arrays.toString(Ad.categories));
            }
            
            System.out.print(questions[j] + " : ");
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

        String[] pictures = choosePictures(scanner);

        Ad tempAd = new Ad (owner, adInfos[0], adInfos[1], pictures, adPrice, currentUser.getRegion(), adInfos[3] , LocalDate.now().toString());

        try {
            tempAd.isValidAd();
        }catch (InvalidAdInformations e) {
            System.out.println(e.getMessage());
            tempAd = null;
        }

        if (tempAd != null) {
            System.out.println("\nAnnonce créé avec succès");
            ads.add(tempAd);
        }
    }

    public static void updateAd(List<User> users, List<Ad> ads) {
        Scanner scanner = new Scanner(System.in);
        String prompt = "\nQuelle annonce souhaitez-vous modifier ? : ";
        int index = listAndSelectAd(ads, prompt);

        while (true) {
            Ad originalAd = ads.get(index);
            Ad adCopy = new Ad(originalAd);

            System.out.println("\nL'annonce sélectionné est :\n" + originalAd);
            printMenu();

            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == 9) {
                    System.out.println("\nSortie de la modification\n");
                    return;
                }

                if (input >= 1 && input <= 8) {
                    processInput(input, adCopy, users, scanner);

                    try {
                        adCopy.isValidAd();
                        ads.set(index, adCopy);
                        System.out.println("\nAnnonce modifié avec succès\n ");

                    } catch (InvalidAdInformations e){
                        System.out.println(e.getMessage() + "\n" + "Données invalides, aucun changement appliqué" );
                    }
                } else {
                    System.out.println("Veuillez entrer un nombre entre 1 et 8");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    private static void printMenu() {
        System.out.print("""
                Quelle information souhaitez-vous modifier ?\
                
                 1. Propriétaire\
                
                 2. Titre\
                
                 3. Description\
                
                 4. Photo(s)\
                
                 5. Prix\
                
                 6. Région\
                
                 7. Catégorie\
                
                 8. Date de publication\
                
                 9. Quitter
                

                Votre choix :\s""");
    }

    private static void processInput(int input, Ad adCopy, List<User> users ,Scanner scanner) {
        switch (input) {
            case 1 -> adCopy.setOwner(newOwner(users));
            case 2 -> adCopy.setTitle(getInput(scanner, "\nNouveau titre : "));
            case 3 -> adCopy.setDescription(getInput(scanner, "\nNouvelle description : "));
            case 4 -> adCopy.setPictures(choosePictures(scanner));
            case 5 -> adCopy.setPrice(getValidPrice(scanner));
            case 6 -> adCopy.setRegion(getInput(scanner, "Nouvelle région : "));
            case 7 -> adCopy.setCategory(getInput(scanner, "\nNouvelle catégorie : "));
            case 8 -> adCopy.setPublicationDate(getInput(scanner, "Nouvelle date de publication (yyyy-mm-dd) : "));
        }
    }

    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static UUID newOwner (List<User> users) {
        String prompt = "À quel utilisateur souhaitez vous attribuer cette annonce ? : ";
        return users.get(listAndSelectUser(users, prompt)).getId();
    }

    private static Integer getValidPrice(Scanner scanner) {
        while (true) {
            System.out.print("\nNouveau prix (sans €) : ");
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) return null;
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier sans ajouter le sigle €.");
            }
        }
    }

    private static String[] choosePictures (Scanner scanner) {
        String[] pictures;

        while (true){
            System.out.print("Combien de photos souhaitez-vous ajouter à votre annonce ? (1 Min / 5 Max) : ");
            String input = scanner.nextLine();
            try {
                int photoCount = Integer.parseInt(input);
                if (photoCount > 0 && photoCount <= 5) {
                    pictures = new String[photoCount];
                    for (int i = 0; i < photoCount; i++) {
                        System.out.print("Lien vers la photo " + (i + 1) + " : ");
                        pictures[i] = scanner.nextLine();
                    }
                    break;
                } else {
                    System.out.println("\nVeuillez entrer un nombre compris entre 1 et 5");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("\nVeuillez entrer un nombre valide");
            }
        }
        return pictures;
    }

    public static void deleteAd (List<Ad> ads) {
        Scanner scanner = new Scanner(System.in);
        String prompt = "\nQuelle annonce souhaitez-vous supprimer ? : ";
        int index = listAndSelectAd(ads, prompt);

        Ad adToDelete = ads.get(index);
        System.out.println("\nL'annonce sélectionné est :\n" + adToDelete);

        System.out.println("Voulez-vous vraiment supprimer cette annonce ?\n1. Oui\n2. Non");
        while (true) {
            System.out.print("Votre choix : ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    if (choice == 1) {
                        ads.remove(index);
                        System.out.println("\nAnnonce supprimé avec succès\n");
                    } else {
                        System.out.println("\nSuppression annulée\n");
                    }
                    break;
                } else {
                    System.out.println("Veuillez entrer 1 ou" + " 2");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier");
            }
        }
    }

    private static int listAndSelectAd (List<Ad> ads, String prompt) {
        Scanner scanner = new Scanner(System.in);

        int adIndex;
        boolean repetition = true;

        while (true) {
            if (repetition) {
                repetition = false;
                System.out.println("\nListe des annonces :");
                for (int i = 0; i < ads.size(); i++) {
                    System.out.println("\n" + (i + 1) + ".");
                    System.out.print("=========================================");
                    System.out.println(ads.get(i).shortToString());
                    System.out.print("=========================================\n");
                }
            }

            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                adIndex = Integer.parseInt(input) - 1;
                adIndex = adIndex >= 0 && adIndex < ads.size() ? adIndex : 0;
                if (adIndex != 0) {
                    break;
                } else {
                    System.out.println("\nVeuillez entrer un nombre entier de la liste des annonces");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("\nVeuillez entrer un nombre entier");
            }
        }
        return adIndex;
    }


}
