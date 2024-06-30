package com.xplit.leboncoin.service.user;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.internal.internalAd.InternalAd;
import com.xplit.leboncoin.internal.internalAd.UtilAd;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.Scanner;

public class UserAdService {

    public static void userCreateAd(Scanner scanner, User selectedUser) { // This method is useless (she can be call directly from UserMode) but render the code more readable
        InternalAd.createAdInternal(scanner, selectedUser);
    }

    public static void userUpdateAd(Scanner scanner, User selectedUser) {
        if (selectedUser.getAds().isEmpty()) {
            System.out.println(TerminalColor.RED + "\nAucune annonce à modifier" + TerminalColor.RESET);
            return;
        }

        String prompt = "Quelle annonce souhaitez-vous modifier ? : ";
        int adToUpdate = UtilAd.fetchAndChooseSelectedUserAds(scanner, selectedUser, prompt);
        InternalAd.updateAdInternal(scanner, null, selectedUser, adToUpdate, false);
    }

    public static void userDeleteAd(Scanner scanner, User selectedUser) {
        if (selectedUser.getAds().isEmpty()) {
            System.out.println(TerminalColor.RED + "\nAucune annonce à supprimer" + TerminalColor.RESET);
            return;
        }

        String prompt = "Quelle annonce souhaitez-vous supprimer ? : ";
        int index = UtilAd.fetchAndChooseSelectedUserAds(scanner, selectedUser, prompt);

        Ad adToDelete = selectedUser.getAds().get(index);
        InternalAd.deleteAdInternal(scanner, selectedUser, adToDelete);
    }

}
