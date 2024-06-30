package com.xplit.leboncoin.internal.internalAd;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidAdInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * The InternalAd class provides internal methods for managing ads,
 * including creating, updating, and deleting ads.
 *
 * @version 1.0
 */
public class InternalAd {

    /**
     * Creates a new ad for the selected user based on input from the scanner.
     *
     * @param scanner the Scanner object for user input
     * @param selectedUser the user creating the ad
     */
    public static void createAdInternal(Scanner scanner, User selectedUser) {
        UUID owner = selectedUser.getId();
        String ownerRegion = selectedUser.getRegion();

        Integer adPrice = null;

        String[] adInfos = new String[4];
        String[] questions = {"Titre (Obligatoire)", "Description (Obligatoire)", "Prix (Sans €) (Obligatoire)", "Catégorie (Obligatoire)"};

        // Collect ad information from input
        for (int i = 0; i < questions.length; i++) {
            if (i == 3) {
                System.out.println(TerminalColor.YELLOW + "\nListe des catégories :\n" + TerminalColor.RESET + Arrays.toString(Ad.categories));
            }

            System.out.print(questions[i] + " : ");
            String input = scanner.nextLine();
            adInfos[i] = input;
        }

        // Parse ad price if possible
        try {
            adPrice = Integer.parseInt(adInfos[2]);
        } catch (NumberFormatException ignored) {
        } // Ignored because the price validity is checked in isValidAd method

        String[] pictures = UtilAd.choosePictures(scanner);

        // Create a temporary ad with the collected information
        Ad tempAd = new Ad(owner, adInfos[0], adInfos[1], pictures, adPrice, ownerRegion, adInfos[3], LocalDate.now().toString());

        // Validate the temporary ad
        try {
            tempAd.isValidAd();
            System.out.println(TerminalColor.GREEN + "\nAnnonce créé avec succès" + TerminalColor.RESET);
            selectedUser.addAd(tempAd);
        } catch (InvalidAdInformations e) {
            System.out.print(TerminalColor.RED + e.getMessage() + TerminalColor.RESET);
        }
    }

    /**
     * Updates the selected ad for the selected user based on input from the scanner.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users
     * @param selectedUser the user updating the ad
     * @param adToUpdate the index of the ad to update
     * @param adminMode true if the update is performed by an admin, false otherwise
     */
    public static void updateAdInternal(Scanner scanner, List<User> users, User selectedUser, int adToUpdate, boolean adminMode) {
        if (selectedUser.getAds().isEmpty()) {
            System.out.println(TerminalColor.RED + "\nAucune annonce à modifier" + TerminalColor.RESET);
            return;
        }

        while (true) {
            Ad originalAd = selectedUser.getAds().get(adToUpdate);
            Ad adCopy = new Ad(selectedUser.getAds().get(adToUpdate));

            System.out.println(TerminalColor.YELLOW + "\nL'annonce sélectionnée est :\n" + TerminalColor.RESET + originalAd);
            if (adminMode) {
                UtilAd.printAdminMenu();
            } else {
                UtilAd.printUserMenu();
            }

            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == (adminMode ? 9 : 6)) {
                    System.out.println(TerminalColor.YELLOW + "\nSortie de la modification" + TerminalColor.RESET);
                    return;
                }

                if ((adminMode && input >= 1 && input <= 8) || (!adminMode && input >= 1 && input <= 5)) {
                    UtilAd.processInput(scanner, users, adCopy, input, adminMode);

                    try {
                        adCopy.isValidAd();
                        if (adminMode && input == 1) {
                            boolean isModify = UtilAd.setNewOwner(originalAd, adCopy, users);
                            if (!isModify) {
                                System.out.println(TerminalColor.RED + "\nL'annonce n'a pas été modifiée, le propriétaire est le même" + TerminalColor.RESET);
                                return;
                            }
                        } else {
                            selectedUser.getAds().set(adToUpdate, adCopy);
                        }

                        System.out.println(TerminalColor.GREEN + "\nAnnonce modifiée avec succès" + TerminalColor.RESET);
                        return;

                    } catch (InvalidAdInformations e) {
                        System.out.println(TerminalColor.RED + e.getMessage() + "\nDonnées invalides, aucun changement appliqué" + TerminalColor.RESET);
                    }
                } else {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre valide" + TerminalColor.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre valide." + TerminalColor.RESET);
            }
        }
    }

    /**
     * Deletes the selected ad for the selected user based on input from the scanner.
     *
     * @param scanner the Scanner object for user input
     * @param selectedUser the user deleting the ad
     * @param adToDelete the ad to delete
     */
    public static void deleteAdInternal(Scanner scanner, User selectedUser, Ad adToDelete) {
        System.out.println(TerminalColor.YELLOW + "\nL'annonce sélectionnée est :\n" + TerminalColor.RESET + adToDelete);
        System.out.println("Voulez-vous vraiment supprimer cette annonce ?\n1. Oui\n2. Non");

        while (true) {
            System.out.print("Votre choix : ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    if (choice == 1) {
                        selectedUser.getAds().remove(adToDelete);
                        System.out.println(TerminalColor.GREEN + "\nAnnonce supprimée avec succès" + TerminalColor.RESET);
                    } else {
                        System.out.println(TerminalColor.RED + "\nSuppression annulée\n" + TerminalColor.RESET);
                    }
                    break;
                } else {
                    System.out.println(TerminalColor.RED + "Veuillez entrer 1 ou 2" + TerminalColor.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "Veuillez entrer un nombre entier" + TerminalColor.RESET);
            }
        }
    }
}
