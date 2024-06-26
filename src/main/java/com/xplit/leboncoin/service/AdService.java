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

/**
 * The AdService class provides methods to manage advertisements.
 */
public class AdService {

    /**
     * Displays all ads in the list.
     *
     * @param ads List of advertisements to display
     */
    public static void showAds(List<Ad> ads) {
        for (int i = 0; i < 40; i++) {
            System.out.println('\n');
        }
        System.out.println("\nListe des annonces :");
        ads.forEach(System.out::println);
    }

    /**
     * Creates a new advertisement and adds it to the list of ads.
     *
     * @param users List of users to assign the ad to
     * @param ads   List of advertisements to add the new ad to
     */
    public static void createAd(Scanner scanner, List<User> users, List<Ad> ads) {
        String prompt = "À quel utilisateur souhaitez vous attribuer cette annonce ? : ";
        int index = listAndSelectUser(scanner, users, prompt);
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
            adInfos[j] = input;
        }

        try {
            adPrice = Integer.parseInt(adInfos[2]);
        } catch (NumberFormatException ignored) {}

        String[] pictures = choosePictures(scanner);

        Ad tempAd = new Ad(owner, adInfos[0], adInfos[1], pictures, adPrice, currentUser.getRegion(), adInfos[3], LocalDate.now().toString());

        try {
            tempAd.isValidAd();
        } catch (InvalidAdInformations e) {
            System.out.println(e.getMessage());
            tempAd = null;
        }

        if (tempAd != null) {
            System.out.println("\nAnnonce créé avec succès");
            ads.add(tempAd);
        }
    }

    /**
     * Updates an existing advertisement.
     *
     * @param users List of users to select the owner from
     * @param ads   List of advertisements to update
     */
    public static void updateAd(Scanner scanner, List<User> users, List<Ad> ads) {
        String prompt = "\nQuelle annonce souhaitez-vous modifier ? : ";
        int index = listAndSelectAd(scanner, ads, prompt);

        while (true) {
            Ad originalAd = ads.get(index);
            Ad adCopy = new Ad(originalAd);

            System.out.println("\nL'annonce sélectionnée est :\n" + originalAd);
            printMenu();

            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == 9) {
                    System.out.println("\nSortie de la modification\n");
                    return;
                }

                if (input >= 1 && input <= 8) {
                    processInput(scanner, users, adCopy ,input);

                    try {
                        adCopy.isValidAd();
                        ads.set(index, adCopy);
                        System.out.println("\nAnnonce modifiée avec succès\n");

                    } catch (InvalidAdInformations e) {
                        System.out.println(e.getMessage() + "\n" + "Données invalides, aucun changement appliqué");
                    }
                } else {
                    System.out.println("Veuillez entrer un nombre entre 1 et 8");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    /**
     * Prints the menu for modifying an ad.
     */
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

    /**
     * Processes the user input for updating the ad.
     *
     * @param input   The user input
     * @param adCopy  The ad to update
     * @param users   The list of users
     * @param scanner The scanner for user input
     */
    private static void processInput(Scanner scanner, List<User> users, Ad adCopy, int input) {
        switch (input) {
            case 1 -> adCopy.setOwner(newOwner(scanner, users));
            case 2 -> adCopy.setTitle(getInput(scanner, "\nNouveau titre : "));
            case 3 -> adCopy.setDescription(getInput(scanner, "\nNouvelle description : "));
            case 4 -> adCopy.setPictures(choosePictures(scanner));
            case 5 -> adCopy.setPrice(getValidPrice(scanner));
            case 6 -> adCopy.setRegion(getInput(scanner, "Nouvelle région : "));
            case 7 -> adCopy.setCategory(getInput(scanner, "\nNouvelle catégorie : "));
            case 8 -> adCopy.setPublicationDate(getInput(scanner, "Nouvelle date de publication (yyyy-mm-dd) : "));
        }
    }

    /**
     * Gets input from the user.
     *
     * @param scanner The scanner for user input
     * @param prompt  The prompt to display to the user
     * @return The user input
     */
    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Selects a new owner for the ad from the list of users.
     *
     * @param users The list of users
     * @return The UUID of the new owner
     */
    private static UUID newOwner(Scanner scanner, List<User> users) {
        String prompt = "À quel utilisateur souhaitez vous attribuer cette annonce ? : ";
        return users.get(listAndSelectUser(scanner, users, prompt)).getId();
    }

    /**
     * Gets a valid price input from the user.
     *
     * @param scanner The scanner for user input
     * @return The valid price input
     */
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

    /**
     * Chooses pictures for the ad.
     *
     * @param scanner The scanner for user input
     * @return The array of picture URLs
     */
    private static String[] choosePictures(Scanner scanner) {
        String[] pictures;

        while (true) {
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

    /**
     * Deletes an advertisement from the list.
     *
     * @param ads List of advertisements to delete from
     */
    public static void deleteAd(Scanner scanner, List<Ad> ads) {
        String prompt = "Quelle annonce souhaitez-vous supprimer ? : ";
        int index = listAndSelectAd(scanner, ads, prompt);

        Ad adToDelete = ads.get(index);
        System.out.println("\nL'annonce sélectionnée est :\n" + adToDelete);

        System.out.println("Voulez-vous vraiment supprimer cette annonce ?\n1. Oui\n2. Non");
        while (true) {
            System.out.print("Votre choix : ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    if (choice == 1) {
                        ads.remove(index);
                        System.out.println("\nAnnonce supprimée avec succès\n");
                    } else {
                        System.out.println("\nSuppression annulée\n");
                    }
                    break;
                } else {
                    System.out.println("Veuillez entrer 1 ou 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier");
            }
        }
    }

    /**
     * Lists and selects an advertisement from the list.
     *
     * @param ads    List of advertisements to select from
     * @param prompt The prompt to display to the user
     * @return The index of the selected advertisement
     */
    private static int listAndSelectAd(Scanner scanner, List<Ad> ads, String prompt) {
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

            System.out.print('\n' + prompt);
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
