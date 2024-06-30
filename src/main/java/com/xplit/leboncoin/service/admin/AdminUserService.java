package com.xplit.leboncoin.service.admin;

import com.xplit.leboncoin.internal.internalUser.InternalUser;
import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.User;

import java.util.List;
import java.util.Scanner;

/**
 * The AdminUserService class provides methods for admin user management,
 * including updating user profiles and deleting user accounts.
 *
 * @version 1.0
 */
public class AdminUserService {

    /**
     * Updates the profile of a selected user.
     * If there are no users, a message is displayed.
     *
     * @param scanner the Scanner object for admin input
     * @param users the list of users
     */
    public static void adminUpdateProfile(Scanner scanner, List<User> users) {
        // Check if there are any users to update
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur n'a été trouvé");
            return;
        }

        // Prompt the admin to choose a user to update
        String prompt = "Quel utilisateur souhaitez-vous modifier ? : ";
        int index = UtilUser.fetchAndChooseUser(scanner, users, prompt);

        // Get the selected user and create a copy of the user
        User selectedUser = users.get(index);
        User userCopy = new User(selectedUser);

        // Call internal method to update the user's profile
        InternalUser.updateProfileInternal(scanner, users, selectedUser, userCopy, index, true);
    }

    /**
     * Deletes the account of a selected user.
     * If there are no users, a message is displayed.
     *
     * @param scanner the Scanner object for admin input
     * @param users the list of users
     */
    public static void adminDeleteAccount(Scanner scanner, List<User> users) {
        // Check if there are any users to delete
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur n'a été trouvé");
            return;
        }

        // Prompt the admin to choose a user to delete
        String prompt = "Quel utilisateur souhaitez-vous supprimer ? : ";
        int index = UtilUser.fetchAndChooseUser(scanner, users, prompt);

        // Call internal method to delete the selected user
        InternalUser.deleteUserInternal(scanner, users, index);
    }
}
