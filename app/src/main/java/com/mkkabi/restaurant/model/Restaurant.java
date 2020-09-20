package com.mkkabi.restaurant.model;

import com.google.firebase.firestore.DocumentId;

import java.util.HashSet;
import java.util.Set;

public class Restaurant {
    @DocumentId
    private String documentId;
    private String name, shortDescription, imageUrl;
    private Set<MenuCategory> menuCategories = new HashSet<>();


    public Restaurant(){}

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
