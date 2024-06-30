package com.xplit.leboncoin.model;

import com.xplit.leboncoin.util.InvalidAdInformations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.UUID;

/**
 * The Ad class represents an advertisement in the Leboncoin application.
 * It contains details about the ad and provides methods for ad validation.
 *
 * @version 1.0
 */
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

    /**
     * Default constructor needed by Jackson.
     */
    public Ad() {
        // Default constructor needed by Jackson
    }

    /**
     * Constructs a new Ad with the specified details.
     *
     * @param owner the UUID of the ad owner
     * @param title the title of the ad
     * @param description the description of the ad
     * @param pictures an array of picture filenames for the ad
     * @param price the price of the ad
     * @param region the region where the ad is located
     * @param category the category of the ad
     * @param publicationDate the publication date of the ad
     */
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

    /**
     * Constructs a new Ad by copying details from another ad.
     *
     * @param adToCopy the ad to copy details from
     */
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

    /**
     * Returns the UUID of the ad owner.
     *
     * @return the UUID of the ad owner
     */
    public UUID getOwner() {
        return owner;
    }

    /**
     * Sets the UUID of the ad owner.
     *
     * @param toUserId the new UUID of the ad owner
     */
    public void setOwner(UUID toUserId) {
        this.owner = toUserId;
    }

    /**
     * Returns the title of the ad.
     *
     * @return the title of the ad
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the ad.
     *
     * @param title the new title of the ad
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the ad.
     *
     * @return the description of the ad
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the ad.
     *
     * @param description the new description of the ad
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the array of picture filenames for the ad.
     *
     * @return the array of picture filenames for the ad
     */
    public String[] getPictures() {
        return pictures;
    }

    /**
     * Sets the array of picture filenames for the ad.
     *
     * @param pictures the new array of picture filenames for the ad
     */
    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    /**
     * Returns the price of the ad.
     *
     * @return the price of the ad
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets the price of the ad.
     *
     * @param price the new price of the ad
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Returns the region where the ad is located.
     *
     * @return the region where the ad is located
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region where the ad is located.
     *
     * @param region the new region where the ad is located
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Returns the category of the ad.
     *
     * @return the category of the ad
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the ad.
     *
     * @param category the new category of the ad
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the publication date of the ad.
     *
     * @return the publication date of the ad
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the publication date of the ad.
     *
     * @param publicationDate the new publication date of the ad
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Returns a string representation of the ad.
     *
     * @return a string representation of the ad
     */
    public String toString() {
        return "Propriétaire: " + this.owner +
                "\nTitre: " + this.title +
                "\nDescription: " + this.description +
                "\nPhotos: " + Arrays.toString(this.pictures) +
                "\nPrix: " + this.price + '€' +
                "\nRégion: " + this.region +
                "\nCatégorie: " + this.category +
                "\nDate de publication: " + this.publicationDate +
                "\n";
    }

    /**
     * Returns a short string representation of the ad.
     *
     * @return a short string representation of the ad
     */
    public String shortToString() {
        return "\nPropriétaire: " + this.owner +
                "\nTitre: " + this.title +
                "\nDescription: " + this.description +
                "\nPrix : " + this.price + '€' +
                "\nCatégorie: " + this.category +
                "\nDate de publication: " + this.publicationDate;
    }

    // Validation methods

    /**
     * Validates the ad details. Throws InvalidAdInformations if any details are invalid.
     *
     * @throws InvalidAdInformations if any ad details are invalid
     */
    public void isValidAd() throws InvalidAdInformations {
        isValidTitle();
        isValidDescription();
        isValidPictures();
        isValidPrice();
        isValidRegion();
        isValidCategory();
        isValidPublicationDate();
    }

    /**
     * Validates the title of the ad. Throws InvalidAdInformations if invalid.
     */
    private void isValidTitle() {
        String adTitle = this.getTitle();
        if (adTitle == null || adTitle.isEmpty() || !checkType(adTitle)) {
            throw new InvalidAdInformations("Titre");
        }
    }

    /**
     * Validates the description of the ad. Throws InvalidAdInformations if invalid.
     */
    private void isValidDescription() {
        String adDescription = this.getDescription();
        if (adDescription == null || adDescription.isEmpty() || !checkType(adDescription)) {
            throw new InvalidAdInformations("Description");
        }
    }

    /**
     * Validates the pictures of the ad. Throws InvalidAdInformations if invalid.
     */
    private void isValidPictures() {
        String[] adPictures = this.getPictures();
        if (adPictures == null || adPictures.length == 0) {
            throw new InvalidAdInformations("Photos");
        } else {
            for (String adPicture : adPictures) {
                if (!adPicture.startsWith("image") || !checkType(adPicture)) {
                    throw new InvalidAdInformations("Photos");
                }
            }
        }
    }

    /**
     * Validates the price of the ad. Throws InvalidAdInformations if invalid.
     */
    private void isValidPrice() {
        Integer adPrice = this.getPrice();
        if (adPrice == null || checkType(String.valueOf(adPrice)) || adPrice < 0 || adPrice >= 10000) {
            throw new InvalidAdInformations("Prix");
        }
    }

    /**
     * Validates the region of the ad. Throws InvalidAdInformations if invalid.
     */
    private void isValidRegion() {
        String adRegion = this.getRegion();
        if (adRegion == null || adRegion.isEmpty() || !checkType(adRegion)) {
            throw new InvalidAdInformations("Région");
        }
    }

    /**
     * Validates the category of the ad. Throws InvalidAdInformations if invalid.
     */
    private void isValidCategory() {
        String adCategory = this.getCategory();
        if (adCategory == null || adCategory.isEmpty() || !checkType(adCategory)) {
            throw new InvalidAdInformations("Catégorie");
        } else {
            int categoriesLength = categories.length;
            adCategory = adCategory.toLowerCase();
            for (int i = 0; i < categoriesLength; i++) {
                if (categories[i].toLowerCase().equals(adCategory)) {
                    this.setCategory(adCategory.substring(0, 1).toUpperCase() + adCategory.substring(1));
                    return;
                } else if (i == categoriesLength - 1) {
                    throw new InvalidAdInformations("Catégorie");
                }
            }
        }
    }

    /**
     * Validates the publication date of the ad. Throws InvalidAdInformations if invalid.
     */
    private void isValidPublicationDate() {
        String adPublicationDate = this.getPublicationDate();
        if (adPublicationDate == null || adPublicationDate.length() != 10) {
            throw new InvalidAdInformations("Date de publication");
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate publicationDate = LocalDate.parse(adPublicationDate, formatter);

                LocalDate today = LocalDate.now();

                if (publicationDate.getYear() < 2023 || publicationDate.isAfter(today)) {
                    throw new InvalidAdInformations("Date de publication");
                }
            } catch (DateTimeParseException e) {
                throw new InvalidAdInformations("Date de publication");
            }
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
