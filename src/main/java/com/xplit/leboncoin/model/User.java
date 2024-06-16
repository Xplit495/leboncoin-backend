package com.xplit.leboncoin.model;

import java.util.UUID;
import com.xplit.leboncoin.util.InvalidUserInformations;

public class User {
    //Properties
    private UUID id;

    private String firstName;

    private String lastName;

    private String username;

    private String mail;

    private String phone;

    private Integer age;

    private String region;

    public User() {//We can think than this constructor is useless,
                  // but it's not, it's used in the UsersRepository class
    }

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

    //Getter and Setter
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

    //Validation methods
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
        if (lastName != null && !lastName.isEmpty() && !checkType(lastName)){
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
        if (userMail == null || userMail.isEmpty() || !checkType(userMail) || !userMail.contains("@") || (!userMail.contains(".com") && !userMail.contains(".fr"))){
            throw new InvalidUserInformations("mail");
        }
    }

    private void isValidPhone() {
        String userPhone = this.getPhone();
        if (userPhone != null && !userPhone.isEmpty() && (checkType(userPhone) || userPhone.length() != 10 || !userPhone.startsWith("0"))){
            throw new InvalidUserInformations("phone");
        }
    }

    private void isValidAge() {
        Integer userAge = this.getAge();
        if (userAge != null && (checkType(String.valueOf(userAge)) || userAge < 17 || userAge > 100)){
            throw new InvalidUserInformations("age");
        }
    }

    private void isValidRegion() {
        String userRegion = this.getRegion();
        if (userRegion == null || userRegion.isEmpty() || !checkType(userRegion)){
                throw new InvalidUserInformations("region");
        }
    }

    private boolean checkType (String value) {
        try {
            Integer.parseInt(value);
            return false;
        }
        catch (NumberFormatException e) {
            return true;
        }
    }

}
