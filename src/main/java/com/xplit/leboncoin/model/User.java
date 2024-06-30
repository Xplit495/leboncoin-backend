package com.xplit.leboncoin.model;

import com.xplit.leboncoin.util.InvalidUserInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    // Properties
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String mail;
    private String phone;
    private Integer age;
    private String region;
    private List<Ad> ads = new ArrayList<>();

    public User() {
        // Default constructor needed by Jackson
    }

    public User(String firstName, String lastName, String username, String mail,
                String phone, Integer age, String region, List<Ad> ads) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.mail = mail;
        this.phone = phone;
        this.age = age;
        this.region = region;
        this.ads = ads;
    }

    public User(User userToCopy) {
        this.id = userToCopy.getId();
        this.firstName = userToCopy.getFirstName();
        this.lastName = userToCopy.getLastName();
        this.username = userToCopy.getUsername();
        this.mail = userToCopy.getMail();
        this.phone = userToCopy.getPhone();
        this.age = userToCopy.getAge();
        this.region = userToCopy.getRegion();
        this.ads = userToCopy.getAds();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Ad> getAds() {
        return ads;
    }

    // Methods
    public void addAd(Ad ad) {
        this.ads.add(ad);
    }

    public void showSelectedUserAds() {
        for (int i = 0; i < 40; i++) {
            System.out.println('\n');
        }

        System.out.println(TerminalColor.YELLOW + "\nListe de vos annonces :" + TerminalColor.RESET);
        if (this.getAds().isEmpty()) {
            System.out.println(TerminalColor.RED + "\nAucune" + TerminalColor.RESET);
        } else {
            for (Ad ad : this.getAds()) {
                System.out.println("\n====================================================================================");
                System.out.print(ad);
                System.out.print("====================================================================================\n\n");
            }
        }
    }

    public void copyFrom(User userToCopy) {
        this.firstName = userToCopy.getFirstName();
        this.lastName = userToCopy.getLastName();
        this.username = userToCopy.getUsername();
        this.mail = userToCopy.getMail();
        this.phone = userToCopy.getPhone();
        this.age = userToCopy.getAge();
        this.region = userToCopy.getRegion();
        this.ads = userToCopy.getAds();
    }

    public String toString() {
        StringBuilder adsString = new StringBuilder();

        if (!this.ads.isEmpty()) {
            int adsSize = this.ads.size();
            for (int i = 0; i < adsSize; i++) {
                adsString.append("\n\n").append("Annonce N°(").append(i + 1).append("): ").append(ads.get(i).shortToString());
            }
        } else {
            adsString.append("null");
        }

        return "ID: " + this.id +
                "\nPrénom: " + this.firstName +
                "\nNom: " + this.lastName +
                "\nPseudo: " + this.username +
                "\nMail: " + this.mail +
                "\nTéléphone: " + this.phone +
                "\nÂge: " + this.age +
                "\nRégion: " + this.region +
                "\nNombre d'annonces : " + this.ads.size() + " ↓ :" + adsString;
    }

    public String shortToString() {
        return "\nID: " + this.id +
                "\nPrénom: " + this.firstName +
                "\nNom: " + this.lastName +
                "\nPseudo: " + this.username +
                "\nMail: " + this.mail;
    }

    // Validation methods
    public void isValidUser() throws InvalidUserInformations {
        isValidFirstName();
        isValidLastName();
        isValidUsername();
        isValidMail();
        isValidPhone();
        isValidAge();
        isValidRegion();
    }

    private void isValidFirstName() {
        String userFirstName = this.getFirstName();
        if (userFirstName == null || userFirstName.isEmpty() || !checkType(userFirstName)) {
            throw new InvalidUserInformations("Prénom");
        }
    }

    private void isValidLastName() {
        String lastName = this.getLastName();
        if (lastName != null && !lastName.isEmpty() && !checkType(lastName)) {
            throw new InvalidUserInformations("Nom");
        }
    }

    private void isValidUsername() {
        String username = this.getUsername();
        if (username == null || username.isEmpty() || !checkType(username)) {
            throw new InvalidUserInformations("Pseudo");
        }
    }

    private void isValidMail() {
        String userMail = this.getMail();
        if (userMail == null || userMail.isEmpty() || !checkType(userMail) || !userMail.contains("@") || (!userMail.contains(".com") && !userMail.contains(".fr"))) {
            throw new InvalidUserInformations("Mail");
        }
    }

    private void isValidPhone() {
        String userPhone = this.getPhone();
        if (userPhone != null && !userPhone.isEmpty() && (checkType(userPhone) || userPhone.length() != 10 || !userPhone.startsWith("0"))) {
            throw new InvalidUserInformations("Téléphone");
        }
    }

    private void isValidAge() {
        Integer userAge = this.getAge();
        if (userAge != null && (checkType(String.valueOf(userAge)) || userAge < 17 || userAge > 100)) {
            throw new InvalidUserInformations("Age");
        }
    }

    private void isValidRegion() {
        String userRegion = this.getRegion();
        if (userRegion == null || userRegion.isEmpty() || !checkType(userRegion)) {
            throw new InvalidUserInformations("Région");
        }
    }

    private boolean checkType(String value) {
        try {
            Integer.parseInt(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

}
