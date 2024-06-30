package com.xplit.leboncoin.service.admin;

import com.xplit.leboncoin.internal.internalAd.InternalAd;
import com.xplit.leboncoin.internal.internalAd.UtilAd;
import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminAdService {

    public static void adminCreateAd(Scanner scanner, List<User> users) {
        String prompt = "À quel utilisateur souhaitez vous attribuer cette annonce ? : ";
        int index = UtilUser.fetchAndChooseUser(scanner, users, prompt);
        User selectedUser = users.get(index);

        InternalAd.createAdInternal(scanner, selectedUser);
    }

    public static void adminUpdateAd(Scanner scanner, List<User> users) {
        String prompt = "\nQuelle annonce souhaitez-vous modifier ? : ";
        int[] indexes = UtilAd.fetchAndChooseUserAds(scanner, users, prompt);
        int adToUpdate = indexes[0];
        int userIndex = indexes[1];

        InternalAd.updateAdInternal(scanner, users, users.get(userIndex), adToUpdate, true);
    }

    public static void adminDeleteAd(Scanner scanner, List<User> users) {
        String prompt = "Quelle annonce souhaitez-vous supprimer ? : ";
        int[] indexes = UtilAd.fetchAndChooseUserAds(scanner, users, prompt);

        int userIndex = indexes[0];
        int adIndex = indexes[1];
        Ad adToDelete = users.get(userIndex).getAds().get(adIndex);

        InternalAd.deleteAdInternal(scanner, users.get(userIndex), adToDelete);
    }

}
