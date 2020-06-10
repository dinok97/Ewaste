package com.example.dinokeylas.ewaste.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class TranModel {
    @Exclude private String tranId;
    private String transactionCode;
    private String userId;
    private String userEmail;
    private String transactionType;
    private Date date;
    private String garbageName;
    private double garbageWeight;
    private String location;
    private GeoPoint latLngLocation;
    private double nominal;
    private String officer;
    private boolean done;
    private String transactionProgress;

    public TranModel(){

    }

    public TranModel(String transactionCode, String userId, String userEmail, String transactionType, Date date, String garbageName, double garbageWeight, String location, GeoPoint latLngLocation, double nominal, String officer, boolean done, String transactionProgress) {
        this.transactionCode = transactionCode;
        this.userId = userId;
        this.userEmail = userEmail;
        this.transactionType = transactionType;
        this.date = date;
        this.garbageName = garbageName;
        this.garbageWeight = garbageWeight;
        this.location = location;
        this.latLngLocation = latLngLocation;
        this.nominal = nominal;
        this.officer = officer;
        this.done = done;
        this.transactionProgress = transactionProgress;
    }

    @Exclude
    public String getTranId() {
        return tranId;
    }

    @Exclude
    public void setTranId(String id) {
        this.tranId = id;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Date getDate() {
        return date;
    }

    public String getGarbageName() {
        return garbageName;
    }

    public double getGarbageWeight() {
        return garbageWeight;
    }

    public String getLocation() {
        return location;
    }

    public GeoPoint getLatLngLocation() {
        return latLngLocation;
    }

    public double getNominal() {
        return nominal;
    }

    public String getOfficer() {
        return officer;
    }

    public boolean isDone() {
        return done;
    }

    public String getTransactionProgress() {
        return transactionProgress;
    }
}
