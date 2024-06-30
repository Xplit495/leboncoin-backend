package com.xplit.leboncoin.service.admin;

import com.xplit.leboncoin.internal.internalUser.InternalUser;
import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminUserService {

    public static void adminUpdateProfile(Scanner scanner, List<User> users) {
        String prompt = "Quel utilisateur souhaitez-vous modifier ? : ";
        int index = UtilUser.fetchAndChooseUser(scanner, users, prompt);
        User selectedUser = users.get(index);
        User userCopy = new User(selectedUser);

        InternalUser.updateProfileInternal(scanner, users, selectedUser, userCopy, index, true);
    }

    public static void adminDeleteAccount(Scanner scanner, List<User> users) {
        String prompt = "Quel utilisateur souhaitez-vous supprimer ? : ";
        int index = UtilUser.fetchAndChooseUser(scanner, users, prompt);

        InternalUser.deleteUserInternal(scanner, users, index);
    }

}
