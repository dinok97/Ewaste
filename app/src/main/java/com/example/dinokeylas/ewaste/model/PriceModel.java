package com.example.dinokeylas.ewaste.model;

import com.google.firebase.firestore.Exclude;

public class PriceModel {
    @Exclude private String id;
    private String garbageName;
    private String garbageType;
    private int directPrice;
    private int savingPrice;

    public PriceModel(){

    }

    public PriceModel(String garbageType, String garbageName, int directPrice, int savingPrice) {
        this.garbageType = garbageType;
        this.garbageName = garbageName;
        this.directPrice = directPrice;
        this.savingPrice = savingPrice;
    }

    @Exclude public void setId(String garbageId) {
        this.id = garbageId;
    }

    @Exclude public String getId() {
        return id;
    }

    public String getGarbageName() {
        return garbageName;
    }

    public String getGarbageType() {
        return garbageType;
    }

    public int getDirectPrice() {
        return directPrice;
    }

    public int getSavingPrice() {
        return savingPrice;
    }
}
