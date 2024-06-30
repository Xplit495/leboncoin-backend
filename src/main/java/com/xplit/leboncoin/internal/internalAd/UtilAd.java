package com.xplit.leboncoin.internal.internalAd;

import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.*;

/**
 * The UtilAd class provides utility methods for managing ads,
 * including displaying ads, fetching and choosing ads, and processing ad input.
 *
 * @version 1.0
 */
public class UtilAd {

    /**
     * Displays all ads from the list of users.
     *
     * @param users the list of users
     */
    public static void showAds(List<User> users) {
        for (int i = 0; i < 40; i++) {
            System.out.println('\n');
        }

        System.out.println(TerminalColor.YELLOW + "\nListe des annonces :" + TerminalColor.RESET);
        for (User user : users) {
            for (Ad ad : user.getAds()) {
                System.out.println("\n====================================================================================");
                System.out.print(ad);
                System.out.print("====================================================================================\n\n");
            }
        }
    }

    /**
     * Prompts the user to select an ad from a list of all users' ads and returns the selected ad's index and user index.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users
     * @param prompt the prompt to display to the user
     * @return an array with the selected ad's index and the user index
     */
    public static int[] fetchAndChooseUserAds(Scanner scanner, List<User> users, String prompt) {
        boolean repetition = true;

        Map<Integer, Integer> adToUserIndexMap = new HashMap<>();
        Map<Integer, Integer> adToAdIndexMap = new HashMap<>();

        int count = 1;

        while (true) {
            if (repetition) {
                repetition = false;

                System.out.println(TerminalColor.YELLOW + "\nListe des annonces :" + TerminalColor.RESET);
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    for (int j = 0; j < user.getAds().size(); j++) {
                        Ad ad = user.getAds().get(j);
                        adToUserIndexMap.put(count, i); // Mapping user index to user ad index
                        adToAdIndexMap.put(count, j);   // Mapping user ad index to user index
                        System.out.println("\n" + TerminalColor.YELLOW + count + "." + TerminalColor.RESET);
                        System.out.print("=========================================");
                        System.out.println(ad.shortToString());
                        System.out.print("=========================================\n");
                        count++;
                    }
                }
            }

            System.out.print('\n' + prompt);
            String input = scanner.nextLine();

            try {
                int adIndex = Integer.parseInt(input);
                if (adIndex >= 1 && adIndex < count) {
                    int userAdIndex = adToAdIndexMap.get(adIndex);
                    int userIndex = adToUserIndexMap.get(adIndex);
                    return new int[]{userAdIndex, userIndex};
                } else {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre de la liste des annonces" + TerminalColor.RESET);
                }
            } catch (NumberFormatException ignored) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entier" + TerminalColor.RESET);
            }
        }
    }

    /**
     * Prompts the user to select an ad from a selected user's ads and returns the selected ad's index.
     *
     * @param scanner the Scanner object for user input
     * @param selectedUser the user whose ads are to be selected
     * @param prompt the prompt to display to the user
     * @return the index of the selected ad
     */
    public static int fetchAndChooseSelectedUserAds(Scanner scanner, User selectedUser, String prompt) {
        boolean repetition = true;

        while (true) {
            if (repetition) {
                repetition = false;
                System.out.println(TerminalColor.YELLOW + "\nListe des annonces :" + TerminalColor.RESET);
                for (int i = 0; i < selectedUser.getAds().size(); i++) {
                    System.out.println("\n" + TerminalColor.YELLOW + (i + 1) + "." + TerminalColor.RESET);
                    System.out.print("=========================================");
                    System.out.println(selectedUser.getAds().get(i).shortToString());
                    System.out.print("=========================================\n");
                }
            }

            System.out.print('\n' + prompt);
            String input = scanner.nextLine();

            try {
                int adIndex = Integer.parseInt(input);
                if (adIndex >= 1 && adIndex <= selectedUser.getAds().size()) {
                    return adIndex - 1;
                } else {
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre de la liste des annonces" + TerminalColor.RESET);
                }
            } catch (NumberFormatException ignored) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entier" + TerminalColor.RESET);
            }
        }
    }

    /**
     * Prints the admin menu for modifying ad information.
     */
    public static void printAdminMenu() {
        System.out.print("""
                Quelle information souhaitez-vous modifier ?
                 1. Propriétaire
                 2. Titre
                 3. Description
                 4. Photo(s)
                 5. Prix
                 6. Région
                 7. Catégorie
                 8. Date de publication
                 9. Quitter

                Votre choix :\s""");
    }

    /**
     * Prints the user menu for modifying their own ad information.
     */
    public static void printUserMenu() {
        System.out.print("""
                Quelle information souhaitez-vous modifier ?
                 1. Titre
                 2. Description
                 3. Photo(s)
                 4. Prix
                 5. Catégorie
                 6. Quitter

                Votre choix :\s""");
    }

    /**
     * Processes the user's input for updating ad information.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users
     * @param adCopy the ad object to update
     * @param input the input choice
     * @param adminMode true if the operation is performed by an admin, false otherwise
     */
    public static void processInput(Scanner scanner, List<User> users, Ad adCopy, int input, boolean adminMode) {
        if (adminMode) {
            switch (input) {
                case 1 -> adCopy.setOwner(newOwner(scanner, users));
                case 2 -> adCopy.setTitle(getInput(scanner, "\nNouveau titre : "));
                case 3 -> adCopy.setDescription(getInput(scanner, "\nNouvelle description : "));
                case 4 -> adCopy.setPictures(choosePictures(scanner));
                case 5 -> adCopy.setPrice(getValidPrice(scanner));
                case 6 -> adCopy.setRegion(getInput(scanner, "\nNouvelle région : "));
                case 7 -> adCopy.setCategory(getInput(scanner, "\nNouvelle catégorie : "));
                case 8 -> adCopy.setPublicationDate(getInput(scanner, "\nNouvelle date de publication (yyyy-mm-dd) : "));
            }
        } else {
            switch (input) {
                case 1 -> adCopy.setTitle(getInput(scanner, "\nNouveau titre : "));
                case 2 -> adCopy.setDescription(getInput(scanner, "\nNouvelle description : "));
                case 3 -> adCopy.setPictures(choosePictures(scanner));
                case 4 -> adCopy.setPrice(getValidPrice(scanner));
                case 5 ->
                        adCopy.setCategory(getInput(scanner, TerminalColor.YELLOW + "\nListe des catégories :\n" + TerminalColor.RESET + Arrays.toString(Ad.categories) + "\nNouvelle catégorie : "));
            }
        }

    }

    /**
     * Prompts the user to select a new owner for an ad and returns the new owner's UUID.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users
     * @return the UUID of the new owner
     */
    private static UUID newOwner(Scanner scanner, List<User> users) {
        String prompt = "À quel utilisateur souhaitez-vous attribuer cette annonce ? : ";
        return users.get(UtilUser.fetchAndChooseUser(scanner, users, prompt)).getId();
    }

    /**
     * Prompts the user for input with the specified prompt.
     *
     * @param scanner the Scanner object for user input
     * @param prompt the prompt to display to the user
     * @return the user's input
     */
    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts the user to choose pictures for an ad and returns the array of picture filenames.
     *
     * @param scanner the Scanner object for user input
     * @return the array of picture filenames
     */
    public static String[] choosePictures(Scanner scanner) {
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
                    System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre compris entre 1 et 5" + TerminalColor.RESET);
                }
            } catch (NumberFormatException ignored) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre valide" + TerminalColor.RESET);
            }
        }
        return pictures;
    }

    /**
     * Prompts the user for a valid price input.
     *
     * @param scanner the Scanner object for user input
     * @return the valid price input
     */
    private static Integer getValidPrice(Scanner scanner) {
        while (true) {
            System.out.print("\nNouveau prix (sans €) : ");
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) return null;
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "\nVeuillez entrer un nombre entier sans ajouter le sigle €." + TerminalColor.RESET);
            }
        }
    }

    /**
     * Updates the owner of an ad if the ad's owner has changed.
     *
     * @param originalAd the original ad
     * @param adCopy the updated ad
     * @param users the list of users
     * @return true if the owner was updated successfully, false otherwise
     */
    public static boolean setNewOwner(Ad originalAd, Ad adCopy, List<User> users) {
        User previousOwner = null;
        User newOwner = null;

        // Find the previous and new owners
        for (User user : users) {
            if (previousOwner != null && newOwner != null) {
                break;
            } else if (user.getId().equals(originalAd.getOwner())) {
                previousOwner = user;
            } else if (user.getId().equals(adCopy.getOwner())) {
                newOwner = user;
            }
        }

        if (previousOwner == null || newOwner == null) {
            return false;
        }

        // Remove the ad from the previous owner and add it to the new owner
        previousOwner.getAds().removeIf(ad ->
                ad.getTitle().equals(originalAd.getTitle()) &&
                        ad.getOwner().equals(originalAd.getOwner())
        );

        newOwner.addAd(adCopy);

        return true;
    }
}
