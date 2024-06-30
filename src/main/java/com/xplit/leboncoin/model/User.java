package com.xplit.leboncoin.model;

import com.xplit.leboncoin.util.InvalidUserInformations;
import com.xplit.leboncoin.util.TerminalColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The User class represents a user in the Leboncoin application.
 * It contains user details and associated ads, and provides methods
 * for user validation and displaying user information.
 *
 * @version 1.0
 */
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

    /**
     * Default constructor needed by Jackson.
     */
    public User() {
        // Default constructor needed by Jackson
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param username the username of the user
     * @param mail the email address of the user
     * @param phone the phone number of the user
     * @param age the age of the user
     * @param region the region of the user
     * @param ads the list of ads associated with the user
     */
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

    /**
     * Constructs a new User by copying details from another user.
     *
     * @param userToCopy the user to copy details from
     */
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

    /**
     * Returns the ID of the user.
     *
     * @return the ID of the user
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the new ID of the user
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the new first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the new last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address of the user
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the email address of the user.
     *
     * @param mail the new email address of the user
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return the phone number of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phone the new phone number of the user
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the age of the user.
     *
     * @return the age of the user
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the user.
     *
     * @param age the new age of the user
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Returns the region of the user.
     *
     * @return the region of the user
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region of the user.
     *
     * @param region the new region of the user
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Returns the list of ads associated with the user.
     *
     * @return the list of ads
     */
    public List<Ad> getAds() {
        return ads;
    }

    // Methods

    /**
     * Adds an ad to the user's list of ads.
     *
     * @param ad the ad to add
     */
    public void addAd(Ad ad) {
        this.ads.add(ad);
    }

    /**
     * Displays the list of ads associated with the user.
     * Clears the terminal before displaying the ads.
     */
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

    /**
     * Copies details from another user to this user.
     *
     * @param userToCopy the user to copy details from
     */
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

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user
     */
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

    /**
     * Returns a short string representation of the user.
     *
     * @return a short string representation of the user
     */
    public String shortToString() {
        return "\nID: " + this.id +
                "\nPrénom: " + this.firstName +
                "\nNom: " + this.lastName +
                "\nPseudo: " + this.username +
                "\nMail: " + this.mail;
    }

    // Validation methods

    /**
     * Validates the user details. Throws InvalidUserInformations if any details are invalid.
     *
     * @throws InvalidUserInformations if any user details are invalid
     */
    public void isValidUser() throws InvalidUserInformations {
        isValidFirstName();
        isValidLastName();
        isValidUsername();
        isValidMail();
        isValidPhone();
        isValidAge();
        isValidRegion();
    }

    /**
     * Validates the first name of the user. Throws InvalidUserInformations if invalid.
     */
    private void isValidFirstName() {
        String userFirstName = this.getFirstName();
        if (userFirstName == null || userFirstName.isEmpty() || !checkType(userFirstName)) {
            throw new InvalidUserInformations("Prénom");
        }
    }

    /**
     * Validates the last name of the user. Throws InvalidUserInformations if invalid.
     */
    private void isValidLastName() {
        String lastName = this.getLastName();
        if (lastName != null && !lastName.isEmpty() && !checkType(lastName)) {
            throw new InvalidUserInformations("Nom");
        }
    }

    /**
     * Validates the username of the user. Throws InvalidUserInformations if invalid.
     */
    private void isValidUsername() {
        String username = this.getUsername();
        if (username == null || username.isEmpty() || !checkType(username)) {
            throw new InvalidUserInformations("Pseudo");
        }
    }

    /**
     * Validates the email address of the user. Throws InvalidUserInformations if invalid.
     */
    private void isValidMail() {
        String userMail = this.getMail();
        if (userMail == null || userMail.isEmpty() || !checkType(userMail) || !userMail.contains("@") || (!userMail.contains(".com") && !userMail.contains(".fr"))) {
            throw new InvalidUserInformations("Mail");
        }
    }

    /**
     * Validates the phone number of the user. Throws InvalidUserInformations if invalid.
     */
    private void isValidPhone() {
        String userPhone = this.getPhone();
        if (userPhone != null && !userPhone.isEmpty() && (checkType(userPhone) || userPhone.length() != 10 || !userPhone.startsWith("0"))) {
            throw new InvalidUserInformations("Téléphone");
        }
    }

    /**
     * Validates the age of the user. Throws InvalidUserInformations if invalid.
     */
    private void isValidAge() {
        Integer userAge = this.getAge();
        if (userAge != null && (checkType(String.valueOf(userAge)) || userAge < 17 || userAge > 100)) {
            throw new InvalidUserInformations("Age");
        }
    }

    /**
     * Validates the region of the user. Throws InvalidUserInformations if invalid.
     */
    private void isValidRegion() {
        String userRegion = this.getRegion();
        if (userRegion == null || userRegion.isEmpty() || !checkType(userRegion)) {
            throw new InvalidUserInformations("Région");
        }
    }

    /**
     * Checks if the given value is of valid type (not a number).
     *
     * @param value the value to check
     * @return true if the value is a valid type, false if it is a number
     */
    private boolean checkType(String value) {
        try {
            Integer.parseInt(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
