package com.xplit.leboncoin.service.user;

import com.xplit.leboncoin.internal.internalUser.InternalUser;
import com.xplit.leboncoin.model.User;

import java.util.List;
import java.util.Scanner;

/**
 * The UserService class provides methods for user profile updates and account deletions.
 *
 * @version 1.0
 */
public class UserService {

    /**
     * Updates the profile of the selected user.
     * This method creates a copy of the selected user and calls an internal method to perform the update.
     *
     * @param scanner the Scanner object for user input
     * @param selectedUser the user whose profile is to be updated
     */
    public static void userUpdateProfile(Scanner scanner, User selectedUser) {
        // Create a copy of the selected user
        User userCopy = new User(selectedUser);

        // Call internal method to update the profile
        InternalUser.updateProfileInternal(scanner, null, selectedUser, userCopy, null, false);
    }

    /**
     * Deletes the account of the user at the specified index in the user list.
     * This method calls an internal method to perform the deletion.
     *
     * @param scanner the Scanner object for user input
     * @param users the list of users
     * @param index the index of the user to be deleted
     */
    public static void userDeleteAccount(Scanner scanner, List<User> users, int index) {
        // Call internal method to delete the user
        InternalUser.deleteUserInternal(scanner, users, index); // This is useless, but it's here to render the code more readable
    }
}
