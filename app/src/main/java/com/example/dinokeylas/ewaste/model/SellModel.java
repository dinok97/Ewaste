package com.example.dinokeylas.ewaste.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class SellModel {
    @Exclude
    private String id;
    private String userId, userEmail, garbageName;
    private double garbageWeight;
    private Date date;
    private GeoPoint location;
    private String transactionProgress;

    public SellModel(String userId, String userEmail, Date date, String garbageName, Double garbageWeight, GeoPoint location, String transactionProgress) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.date = date;
        this.garbageName = garbageName;
        this.garbageWeight = garbageWeight;
        this.location = location;
        this.transactionProgress = transactionProgress;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Date getDate() {
        return date;
    }

    public String getGarbageName() {
        return garbageName;
    }

    public Double getGarbageWeight() {
        return garbageWeight;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public String getTransactionProgress() {
        return transactionProgress;
    }
}
