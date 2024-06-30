package com.xplit.leboncoin.service.user;

import com.xplit.leboncoin.internal.internalUser.InternalUser;
import com.xplit.leboncoin.model.User;

import java.util.List;
import java.util.Scanner;

public class UserService {

    public static void userUpdateProfile(Scanner scanner, User selectedUser) {
        User userCopy = new User(selectedUser);

        InternalUser.updateProfileInternal(scanner, null, selectedUser, userCopy, null, false);
    }

    public static void userDeleteAccount(Scanner scanner, List<User> users, int index) {
        InternalUser.deleteUserInternal(scanner, users, index); // This is useless, but it's here to render the code more readable
    }

}
