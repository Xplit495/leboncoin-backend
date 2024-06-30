package com.xplit.leboncoin.service.user;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.internal.internalAd.InternalAd;
import com.xplit.leboncoin.internal.internalAd.UtilAd;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.Scanner;

/**
 * The UserAdService class provides methods for user ad management,
 * including creating, updating, and deleting ads.
 *
 * @version 1.0
 */
public class UserAdService {

    /**
     * Creates a new ad for the selected user.
     * This method is called to make the code more readable,
     * even though it can be called directly from UserMode.
     *
     * @param scanner the Scanner object for user input
     * @param selectedUser the user creating the ad
     */
    public static void userCreateAd(Scanner scanner, User selectedUser) {
        // Call internal method to create a new ad
        InternalAd.createAdInternal(scanner, selectedUser);
    }

    /**
     * Updates an existing ad for the selected user.
     * If the user has no ads, a message is displayed.
     *
     * @param scanner the Scanner object for user input
     * @param selectedUser the user updating the ad
     */
    public static void userUpdateAd(Scanner scanner, User selectedUser) {
        // Check if the user has any ads to update
        if (selectedUser.getAds().isEmpty()) {
            System.out.println(TerminalColor.RED + "\nAucune annonce à modifier" + TerminalColor.RESET);
            return;
        }

        // Prompt the user to choose an ad to update
        String prompt = "Quelle annonce souhaitez-vous modifier ? : ";
        int adToUpdate = UtilAd.fetchAndChooseSelectedUserAds(scanner, selectedUser, prompt);

        // Call internal method to update the selected ad
        InternalAd.updateAdInternal(scanner, null, selectedUser, adToUpdate, false);
    }

    /**
     * Deletes an existing ad for the selected user.
     * If the user has no ads, a message is displayed.
     *
     * @param scanner the Scanner object for user input
     * @param selectedUser the user deleting the ad
     */
    public static void userDeleteAd(Scanner scanner, User selectedUser) {
        // Check if the user has any ads to delete
        if (selectedUser.getAds().isEmpty()) {
            System.out.println(TerminalColor.RED + "\nAucune annonce à supprimer" + TerminalColor.RESET);
            return;
        }

        // Prompt the user to choose an ad to delete
        String prompt = "Quelle annonce souhaitez-vous supprimer ? : ";
        int index = UtilAd.fetchAndChooseSelectedUserAds(scanner, selectedUser, prompt);

        // Get the ad to delete
        Ad adToDelete = selectedUser.getAds().get(index);

        // Call internal method to delete the selected ad
        InternalAd.deleteAdInternal(scanner, selectedUser, adToDelete);
    }
}
