package com.xplit.leboncoin.model;

import com.xplit.leboncoin.util.InvalidAdInformations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    //Validation methods
    public void isValidAd() throws InvalidAdInformations {
        isValidTitle();
        isValidDescription();
        isValidPictures();
        isValidPrice();
        isValidRegion();
        isValidCategory();
        isValidPublicationDate();
    }

    private void isValidTitle() {
        String adTitle = this.getTitle();
        if (adTitle == null || adTitle.isEmpty() || !checkType(adTitle)) {
            throw new InvalidAdInformations("title");
        }
    }

    private void isValidDescription() {
        String adDescription = this.getDescription();
        if (adDescription == null || adDescription.isEmpty() || !checkType(adDescription)) {
            throw new InvalidAdInformations("description");
        }
    }

    private void isValidPictures() {
        String[] adPictures = this.getPictures();
        if (adPictures != null && adPictures.length > 0) {
            for (String adPicture : adPictures) {
                    if (!adPicture.startsWith("image") || !checkType(adPicture)) {
                        throw new InvalidAdInformations("pictures");
                    }
                }
        }else {
            throw new InvalidAdInformations("pictures");
        }
    }

    private void isValidPrice() {
        Integer adPrice = this.getPrice();
        if (adPrice == null || checkType(String.valueOf(adPrice)) || adPrice < 0 || adPrice >= 10000) {
            throw new InvalidAdInformations("price");
        }
    }

    private void isValidRegion() {
        String adRegion = this.getRegion();
        if (adRegion == null || adRegion.isEmpty() || !checkType(adRegion)) {
            throw new InvalidAdInformations("region");
        }
    }

    private void isValidCategory() {
        String adCategory = this.getCategory();
        if (adCategory == null || adCategory.isEmpty() || !checkType(adCategory)) {
            throw new InvalidAdInformations("category");
        }else {
            int categoriesLength = categories.length;
            for (int i = 0; i < categoriesLength; i++){
                if (categories[i].equals(adCategory)){
                    return;
                }else if (i == categoriesLength - 1){
                    throw new InvalidAdInformations("category");
                }
            }
        }
    }

    private void isValidPublicationDate() {
        String adPublicationDate = this.getPublicationDate();
        if (adPublicationDate == null || adPublicationDate.length() != 10) {
            throw new InvalidAdInformations("publication date");
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate publicationDate = LocalDate.parse(adPublicationDate, formatter);

                LocalDate today = LocalDate.now();

                if (publicationDate.getYear() < 2023 || publicationDate.isAfter(today)) {
                    throw new InvalidAdInformations("publication date");
                }
            } catch (DateTimeParseException e) {
                throw new InvalidAdInformations("publication date");
            }
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
