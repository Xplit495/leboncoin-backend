package com.xplit.leboncoin.model;

import com.xplit.leboncoin.util.InvalidAdInformations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.UUID;

public class Ad {
    // Properties
    private UUID owner;
    private String title;
    private String description;
    private String[] pictures;
    private Integer price;
    private String region;
    private String category;
    private String publicationDate;
    public static final String[] categories = {
            "Véhicules",
            "Immobilier",
            "Multimédia",
            "Maison",
            "Loisirs",
            "Matériel professionnel",
            "Emploi",
            "Services",
            "Sport",
            "Jardin",
            "Informatique",
            "Electroménager",
    };

    public Ad() {
        // Default constructor needed by Jackson
    }

    public Ad(UUID owner, String title, String description, String[] pictures, Integer price,
              String region, String category, String publicationDate) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.pictures = pictures;
        this.price = price;
        this.region = region;
        this.category = category;
        this.publicationDate = publicationDate;
    }

    public Ad(Ad adToCopy) {
        this.owner = adToCopy.getOwner();
        this.title = adToCopy.getTitle();
        this.description = adToCopy.getDescription();
        this.pictures = adToCopy.getPictures();
        this.price = adToCopy.getPrice();
        this.region = adToCopy.getRegion();
        this.category = adToCopy.getCategory();
        this.publicationDate = adToCopy.getPublicationDate();
    }

    // Getters and Setters

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID toUserId) {
        this.owner = toUserId;
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
        return "Propriétaire: " + this.owner +
                "\nTitre: " + this.title +
                "\nDescription: " + this.description +
                "\nPhotos: " + Arrays.toString(this.pictures) +
                "\nPrix: " + this.price +
                "\nRégion: " + this.region +
                "\nCatégorie: " + this.category +
                "\nDate de publication: " + this.publicationDate +
                "\n";
    }

    public String shortToString() {
        return "\nPropriétaire: " + this.owner +
                "\nTitre: " + this.title +
                "\nDescription: " + this.description +
                "\nPrix : " + this.price +
                "\nCatégorie: " + this.category +
                "\nDate de publication: " + this.publicationDate;
    }

    // Validation methods

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
        if (adPictures == null || adPictures.length == 0) {
            throw new InvalidAdInformations("pictures");
        } else {
            for (String adPicture : adPictures) {
                if (!adPicture.startsWith("image") || !checkType(adPicture)) {
                    throw new InvalidAdInformations("pictures");
                }
            }
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
        } else {
            int categoriesLength = categories.length;
            adCategory = adCategory.toLowerCase();
            for (int i = 0; i < categoriesLength; i++) {
                if (categories[i].toLowerCase().equals(adCategory)) {
                    this.setCategory(adCategory.substring(0, 1).toUpperCase() + adCategory.substring(1));
                    return;
                } else if (i == categoriesLength - 1) {
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
