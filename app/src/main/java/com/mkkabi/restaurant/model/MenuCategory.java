package com.mkkabi.restaurant.model;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.List;

public class MenuCategory {
    @DocumentId
    private String documentId;
    private String descriptionShort, name, imageUrl;
    private List<Dish> dishList = new ArrayList<>();

    public MenuCategory() {}

    public String getDocumentId() {
        return documentId;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
