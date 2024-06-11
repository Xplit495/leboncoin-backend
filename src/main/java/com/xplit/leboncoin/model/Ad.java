package com.xplit.leboncoin.model;

import java.util.Arrays;
import java.util.UUID;

public class Ad {
    //Properties
    private UUID publishBy;

    private String title;

    private String description;

    private String[] pictures;

    private Integer price;

    private String region;

    private String category;

    private String publicationDate;

    public static String[] categories = {
            "Véhicules",
            "Immobilier",
            "Multimédia",
            "Maison",
            "Loisirs",
            "Materiel professionnel",
            "Emploi",
            "Services",
            "Sport",
            "Jardin",
            "Informatique",
            "Electroménager",

    };

    //Getter and Setter
    public UUID publishBy() {
        return publishBy;
    }

    public void setPublishBy(UUID toUserId) {
        this.publishBy = toUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPictures() {
        return pictures;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String toString() {
        return "\nAd{" +
                "\nToUserId: " + this.publishBy +
                "\nTitle: " + this.title +
                "\nDescription: " + this.description  +
                "\nPictures: " + Arrays.toString(this.pictures) +
                "\nPrice: " + this.price +
                "\nRegion: " + this.region +
                "\nCategory: " + this.category +
                "\nPublicationDate: " + this.publicationDate +
                "}\n";
    }

}
