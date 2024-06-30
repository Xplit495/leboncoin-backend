package com.xplit.leboncoin.service.admin;

import com.xplit.leboncoin.internal.internalAd.InternalAd;
import com.xplit.leboncoin.internal.internalAd.UtilAd;
import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;

import java.util.List;
import java.util.Scanner;

/**
 * The AdminAdService class provides methods for admin ad management,
 * including creating, updating, and deleting ads for users.
 *
 * @version 1.0
 */
public class AdminAdService {

    /**
     * Creates a new ad for a selected user.
     * If there are no users, a message is displayed.
     *
     * @param scanner the Scanner object for admin input
     * @param users the list of users
     */
    public static void adminCreateAd(Scanner scanner, List<User> users) {
        // Check if there are any users to assign the ad to
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur n'a été trouvé");
            return;
        }

        // Prompt the admin to choose a user to assign the ad to
        String prompt = "À quel utilisateur souhaitez vous attribuer cette annonce ? : ";
        int index = UtilUser.fetchAndChooseUser(scanner, users, prompt);
        User selectedUser = users.get(index);

        // Call internal method to create a new ad for the selected user
        InternalAd.createAdInternal(scanner, selectedUser);
    }

    /**
     * Updates an existing ad for a selected user.
     * If there are no users, a message is displayed.
     *
     * @param scanner the Scanner object for admin input
     * @param users the list of users
     */
    public static void adminUpdateAd(Scanner scanner, List<User> users) {
        // Check if there are any users to update the ad for
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur n'a été trouvé");
            return;
        }

        // Prompt the admin to choose an ad to update
        String prompt = "Quelle annonce souhaitez-vous modifier ? : ";
        int[] indexes = UtilAd.fetchAndChooseUserAds(scanner, users, prompt);
        int adToUpdate = indexes[0];
        int userIndex = indexes[1];

        // Call internal method to update the selected ad
        InternalAd.updateAdInternal(scanner, users, users.get(userIndex), adToUpdate, true);
    }

    /**
     * Deletes an existing ad for a selected user.
     * If there are no users, a message is displayed.
     *
     * @param scanner the Scanner object for admin input
     * @param users the list of users
     */
    public static void adminDeleteAd(Scanner scanner, List<User> users) {
        // Check if there are any users to delete the ad for
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur n'a été trouvé");
            return;
        }

        // Prompt the admin to choose an ad to delete
        String prompt = "Quelle annonce souhaitez-vous supprimer ? : ";
        int[] indexes = UtilAd.fetchAndChooseUserAds(scanner, users, prompt);

        int adIndex = indexes[0];
        int userIndex = indexes[1];
        Ad adToDelete = users.get(userIndex).getAds().get(adIndex);

        // Call internal method to delete the selected ad
        InternalAd.deleteAdInternal(scanner, users.get(userIndex), adToDelete);
    }
}
