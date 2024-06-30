package com.xplit.leboncoin.service;

import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.InvalidAdInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.time.LocalDate;
import java.util.*;

import static com.xplit.leboncoin.service.UserService.listAndSelectUser;

public class AdService {

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

    public static void showSelectedUserAds(User selectedUser) {
        for (int i = 0; i < 40; i++) {
            System.out.println('\n');
        }

        System.out.println(TerminalColor.YELLOW + "\nListe de vos annonces :" + TerminalColor.RESET);
        if (selectedUser.getAds().isEmpty()) {
            System.out.println(TerminalColor.RED + "\nAucune" + TerminalColor.RESET);
        } else {
            for (Ad ad : selectedUser.getAds()) {
                System.out.println("\n====================================================================================");
                System.out.print(ad);
                System.out.print("====================================================================================\n\n");
            }
        }
    }

    public static void adminCreateAd(Scanner scanner, List<User> users) {
        String prompt = "À quel utilisateur souhaitez vous attribuer cette annonce ? : ";
        int index = listAndSelectUser(scanner, users, prompt);
        User selectedUser = users.get(index);

        createAdInternal(scanner, selectedUser);
    }

    public static void userCreateAd(Scanner scanner, User selectedUser) { // This method is useless (she can be call directly from UserMode) but render the code more readable
        createAdInternal(scanner, selectedUser);
    }

    private static void createAdInternal(Scanner scanner, User selectedUser) {
        UUID owner = selectedUser.getId();
        String ownerRegion = selectedUser.getRegion();

        Integer adPrice = null;

        String[] adInfos = new String[4];
        String[] questions = {"Titre (Obligatoire)", "Description (Obligatoire)", "Prix (Sans €) (Obligatoire)", "Catégorie (Obligatoire)"};

        for (int i = 0; i < questions.length; i++) {
            if (i == 3) {
                System.out.println(TerminalColor.YELLOW + "\nListe des catégories :\n" + TerminalColor.RESET + Arrays.toString(Ad.categories));
            }

            System.out.print(questions[i] + " : ");
            String input = scanner.nextLine();
            adInfos[i] = input;
        }

        try {
            adPrice = Integer.parseInt(adInfos[2]);
        } catch (NumberFormatException ignored) {
        } // Ignored because the price validity is checked in isValidAd method

        String[] pictures = choosePictures(scanner);

        Ad tempAd = new Ad(owner, adInfos[0], adInfos[1], pictures, adPrice, ownerRegion, adInfos[3], LocalDate.now().toString());

        try {
            tempAd.isValidAd();
            System.out.println(TerminalColor.GREEN + "\nAnnonce créé avec succès" + TerminalColor.RESET);
            selectedUser.addAd(tempAd);
        } catch (InvalidAdInformations e) {
            System.out.println(TerminalColor.RED + e.getMessage() + TerminalColor.RESET);
        }
    }

    public static void adminUpdateAd(Scanner scanner, List<User> users) {
        String prompt = "\nQuelle annonce souhaitez-vous modifier ? : ";
        int[] indexes = fetchAndChooseUserAds(scanner, users, prompt);
        int adToUpdate = indexes[0];
        int userIndex = indexes[1];

        updateAdInternal(scanner, users, users.get(userIndex), adToUpdate, true);
    }

    public static void userUpdateAd(Scanner scanner, User selectedUser) {
        int adToUpdate = fetchAndChooseSelectedUserAds(scanner, selectedUser, "\nQuelle annonce souhaitez-vous modifier ? : ");
        updateAdInternal(scanner, null, selectedUser, adToUpdate, false);
    }

    private static void updateAdInternal(Scanner scanner, List<User> users, User selectedUser, int adToUpdate, boolean adminMode) {
        if (selectedUser.getAds().isEmpty()) {
            System.out.println(TerminalColor.RED + "\nAucune annonce à modifier" + TerminalColor.RESET);
            return;
        }

        while (true) {
            Ad originalAd = selectedUser.getAds().get(adToUpdate);
            Ad adCopy = new Ad(selectedUser.getAds().get(adToUpdate));

            System.out.println(TerminalColor.YELLOW + "\nL'annonce sélectionnée est :\n" + TerminalColor.RESET + originalAd);
            if (adminMode) {
                printAdminMenu();
            } else {
                printUserMenu();
            }

            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input == (adminMode ? 9 : 6)) {
                    System.out.println(TerminalColor.YELLOW + "\nSortie de la modification" + TerminalColor.RESET);
                    return;
                }

                if ((adminMode && input >= 1 && input <= 8) || (!adminMode && input >= 1 && input <= 5)) {
                    processInput(scanner, users, adCopy, input, adminMode);

                    try {
                        adCopy.isValidAd();
                        if (adminMode && input == 1) {
                            boolean isModify = setNewOwner(originalAd, adCopy, users);
                            if (!isModify) {
                                System.out.println(TerminalColor.RED + "\nL'annonce n'a pas été modifiée, le propriétaire est le même" + TerminalColor.RESET);
                                return;
                            }
                        } else {
                            selectedUser.getAds().set(adToUpdate, adCopy);
                        }

                        System.out.println(TerminalColor.GREEN + "\nAnnonce modifiée avec succès" + TerminalColor.RESET);
                    } catch (InvalidAdInformations e) {
                        System.out.println(TerminalColor.RED + e.getMessage() + "\n" + "Données invalides, aucun changement appliqué" + TerminalColor.RESET);
                    }
                } else {
                    System.out.println(TerminalColor.RED + "Veuillez entrer un nombre valide" + TerminalColor.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "Veuillez entrer un nombre valide." + TerminalColor.RESET);
            }
        }
    }

    private static void printAdminMenu() {
        System.out.print("""
                Quelle information souhaitez-vous modifier ?\

                 1. Propriétaire\

                 2. Titre\

                 3. Description\

                 4. Photo(s)\

                 5. Prix\

                 6. Région\

                 7. Catégorie\

                 8. Date de publication\

                 9. Quitter


                Votre choix :\s""");
    }

    private static void printUserMenu() {
        System.out.print("""
                Quelle information souhaitez-vous modifier ?\

                 1. Titre\

                 2. Description\

                 3. Photo(s)\

                 4. Prix\

                 5. Catégorie\

                 6. Quitter


                Votre choix :\s""");
    }

    private static void processInput(Scanner scanner, List<User> users, Ad adCopy, int input, boolean adminMode) {
        if (adminMode) {
            switch (input) {
                case 1 -> adCopy.setOwner(newOwner(scanner, users));
                case 2 -> adCopy.setTitle(getInput(scanner, "\nNouveau titre : "));
                case 3 -> adCopy.setDescription(getInput(scanner, "\nNouvelle description : "));
                case 4 -> adCopy.setPictures(choosePictures(scanner));
                case 5 -> adCopy.setPrice(getValidPrice(scanner));
                case 6 -> adCopy.setRegion(getInput(scanner, "Nouvelle région : "));
                case 7 -> adCopy.setCategory(getInput(scanner, "\nNouvelle catégorie : "));
                case 8 -> adCopy.setPublicationDate(getInput(scanner, "Nouvelle date de publication (yyyy-mm-dd) : "));
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

    private static UUID newOwner(Scanner scanner, List<User> users) {
        String prompt = "À quel utilisateur souhaitez-vous attribuer cette annonce ? : ";
        return users.get(listAndSelectUser(scanner, users, prompt)).getId();
    }

    private static boolean setNewOwner(Ad originalAd, Ad adCopy, List<User> users) {
        User previousOwner = null;
        User newOwner = null;

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

        int maxLength = Math.max(Objects.requireNonNull(previousOwner).getAds().size(), Objects.requireNonNull(newOwner).getAds().size());

        for (int i = maxLength - 1; i >= 0; i--) {
            if (i < previousOwner.getAds().size()) {
                if (previousOwner.getAds().get(i).getTitle().equals(originalAd.getTitle()) && previousOwner.getAds().get(i).getOwner().equals(originalAd.getOwner())) {
                    previousOwner.getAds().remove(i);
                }
            }
        }
        newOwner.addAd(adCopy);

        return true;
    }

    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static String[] choosePictures(Scanner scanner) {
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

    private static Integer getValidPrice(Scanner scanner) {
        while (true) {
            System.out.print("\nNouveau prix (sans €) : ");
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) return null;
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(TerminalColor.RED + "Veuillez entrer un nombre entier sans ajouter le sigle €." + TerminalColor.RESET);
            }
        }
    }

    public static void adminDeleteAd(Scanner scanner, List<User> users) {
        String prompt = "Quelle annonce souhaitez-vous supprimer ? : ";
        int[] indexes = fetchAndChooseUserAds(scanner, users, prompt);

        int userIndex = indexes[0];
        int adIndex = indexes[1];
        Ad adToDelete = users.get(userIndex).getAds().get(adIndex);

        deleteAdInternal(scanner, users.get(userIndex), adToDelete);
    }

    public static void userDeleteAd(Scanner scanner, User selectedUser) {
        String prompt = "Quelle annonce souhaitez-vous supprimer ? : ";
        int index = fetchAndChooseSelectedUserAds(scanner, selectedUser, prompt);

        Ad adToDelete = selectedUser.getAds().get(index);

        deleteAdInternal(scanner, selectedUser, adToDelete);
    }

    private static void deleteAdInternal(Scanner scanner, User selectedUser, Ad adToDelete) {
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

    private static int[] fetchAndChooseUserAds(Scanner scanner, List<User> users, String prompt) {
        int adIndex;
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
                adIndex = Integer.parseInt(input);
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


    private static int fetchAndChooseSelectedUserAds(Scanner scanner, User selectedUser, String prompt) {
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

}
