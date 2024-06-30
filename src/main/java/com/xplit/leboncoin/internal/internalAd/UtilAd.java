package com.xplit.leboncoin.internal.internalAd;

import com.xplit.leboncoin.internal.internalUser.UtilUser;
import com.xplit.leboncoin.model.Ad;
import com.xplit.leboncoin.model.User;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.*;

public class UtilAd {

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

    public static int[] fetchAndChooseUserAds(Scanner scanner, List<User> users, String prompt) {
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

    public static void printAdminMenu() {
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

    public static void printUserMenu() {
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

    public static void processInput(Scanner scanner, List<User> users, Ad adCopy, int input, boolean adminMode) {
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
        return users.get(UtilUser.fetchAndChooseUser(scanner, users, prompt)).getId();
    }

    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

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

    public static boolean setNewOwner(Ad originalAd, Ad adCopy, List<User> users) {
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

}
