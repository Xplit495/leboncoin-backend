package com.xplit.leboncoin.model;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import com.xplit.leboncoin.util.InvalidUserInformations;

/**
 * The User class represents a user with various properties
 * such as id, first name, last name, username, mail, phone, age, and region.
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

    /**
     * Default constructor for User.
     * Used by Jackson for JSON serialization/deserialization.
     */
    public User() {
        // Default constructor needed by Jackson
    }

    /**
     * Parameterized constructor for creating a User instance.
     *
     * @param firstName First name of the user
     * @param lastName  Last name of the user
     * @param username  Username of the user
     * @param mail      Email of the user
     * @param phone     Phone number of the user
     * @param age       Age of the user
     * @param region    Region of the user
     */
    public User(String firstName, String lastName, String username, String mail,
                String phone, Integer age, String region) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.mail = mail;
        this.phone = phone;
        this.age = age;
        this.region = region;
    }

    /**
     * Copy constructor for creating a User instance from an existing User.
     *
     * @param userToCopy User instance to copy
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
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id != null) {
            this.id = id;
        }
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

    /**
     * Returns a string representation of the User instance.
     *
     * @return String representation of the User
     */
    public String toString() {
        return "\nUtilisateur\n{" +
                "\nID: " + this.id +
                "\nPrénom: " + this.firstName +
                "\nNom: " + this.lastName +
                "\nPseudo: " + this.username +
                "\nMail: " + this.mail +
                "\nTéléphone: " + this.phone +
                "\nÂge: " + this.age +
                "\nRégion: " + this.region
                + "\n}\n";
    }

    /**
     * Returns a short string representation of the User instance.
     *
     * @return Short string representation of the User
     */
    public String shortToString() {
        return "\nID: " + this.id +
                "\nPrénom: " + this.firstName +
                "\nNom: " + this.lastName +
                "\nPseudo: " + this.username +
                "\nMail: " + this.mail;
    }

    /**
     * Generates a new UUID for the user and updates the owner ID in associated ads.
     *
     * @param scanner Scanner object for user input
     * @param ads     List of ads to update
     */
    public void newId(Scanner scanner, List<Ad> ads) {
        System.out.println("\nPour des raisons de sécurité, l'ID ne peut pas être modifié.\nVoulez-vous en générer un nouveau à la place ?\n1. Oui\n2. Non");
        while (true) {
            System.out.print("Votre choix : ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    if (choice == 1) {
                        UUID newId = UUID.randomUUID();
                        UUID idToSearch = this.getId();

                        this.setId(newId);

                        for (Ad ad : ads) {
                            if (ad.getOwner().equals(idToSearch)) {
                                ad.setOwner(newId);
                            }
                        }
                    }
                    break;
                } else {
                    System.out.println("Veuillez entrer 1 ou 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier");
            }
        }
    }

    // Validation methods

    /**
     * Validates the User instance.
     *
     * @throws InvalidUserInformations if any user information is invalid
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

    private void isValidFirstName() {
        String userFirstName = this.getFirstName();
        if (userFirstName == null || userFirstName.isEmpty() || !checkType(userFirstName)) {
            throw new InvalidUserInformations("first name");
        }
    }

    private void isValidLastName() {
        String lastName = this.getLastName();
        if (lastName != null && !lastName.isEmpty() && !checkType(lastName)) {
            throw new InvalidUserInformations("last name");
        }
    }

    private void isValidUsername() {
        String username = this.getUsername();
        if (username == null || username.isEmpty() || !checkType(username)) {
            throw new InvalidUserInformations("username");
        }
    }

    private void isValidMail() {
        String userMail = this.getMail();
        if (userMail == null || userMail.isEmpty() || !checkType(userMail) || !userMail.contains("@") || (!userMail.contains(".com") && !userMail.contains(".fr"))) {
            throw new InvalidUserInformations("mail");
        }
    }

    private void isValidPhone() {
        String userPhone = this.getPhone();
        if (userPhone != null && !userPhone.isEmpty() && (checkType(userPhone) || userPhone.length() != 10 || !userPhone.startsWith("0"))) {
            throw new InvalidUserInformations("phone");
        }
    }

    private void isValidAge() {
        Integer userAge = this.getAge();
        if (userAge != null && (checkType(String.valueOf(userAge)) || userAge < 17 || userAge > 100)) {
            throw new InvalidUserInformations("age");
        }
    }

    private void isValidRegion() {
        String userRegion = this.getRegion();
        if (userRegion == null || userRegion.isEmpty() || !checkType(userRegion)) {
            throw new InvalidUserInformations("region");
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
