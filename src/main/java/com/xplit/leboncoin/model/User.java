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
        return "{\nUser" +
                "\nID: " + this.id +
                "\nFirstName: " + this.firstName +
                "\nLastName: " + this.lastName +
                "\nUsername: " + this.username +
                "\nMail: " + this.mail +
                "\nPhone: " + this.phone +
                "\nAge: " + this.age +
                "\nRegion: " + this.region
                + "}\n";
    }

    //Validation methods
    public void isValidUser() throws InvalidUserInformations {
        isValidFirstName();
        isValidLastName();
        isValidUsername();
        isValidMail();
        isValidRegion();
        isValidAge();
        isValidPhone();
    }

    private void isValidFirstName() {
        String userFirstName = this.getFirstName();
        if (userFirstName == null || userFirstName.isEmpty() || !checkType(userFirstName)) {
            throw new InvalidUserInformations("first name");
        }
    }

    private void isValidLastName() {
        String lastName = this.getLastName();
        if (lastName != null && (lastName.isEmpty() || !checkType(lastName))){
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

    private void isValidRegion() {
        String userRegion = this.getRegion();
        if (userRegion == null || userRegion.isEmpty() || !checkType(userRegion)){
                throw new InvalidUserInformations("region");
        }
    }

    private void isValidAge() {
        Integer userAge = this.getAge();
        if (userAge != null && (checkType(String.valueOf(userAge)) || userAge < 17 || userAge > 100)){
            throw new InvalidUserInformations("age");
        }
    }

    private void isValidPhone() {
        String userPhone = this.getPhone();
        if (userPhone != null && (userPhone.isEmpty() || checkType(userPhone) || userPhone.length() != 10)) {
            throw new InvalidUserInformations("phone");
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
