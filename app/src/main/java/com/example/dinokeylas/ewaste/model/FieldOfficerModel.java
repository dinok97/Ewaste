package com.example.dinokeylas.ewaste.model;

import com.google.firebase.firestore.Exclude;

public class FieldOfficerModel {

    @Exclude
    private String id;
    private String fOName, fOaddress, fOemailAddress, fOprofileImageUrl, fOphoneNumber, password;

    public FieldOfficerModel () {

    }

    public FieldOfficerModel(String fOName, String fOaddress, String fOemailAddress, String fOprofileImageUrl, String fOphoneNumber, String password) {
        this.fOName = fOName;
        this.fOaddress = fOaddress;
        this.fOemailAddress = fOemailAddress;
        this.fOprofileImageUrl = fOprofileImageUrl;
        this.fOphoneNumber = fOphoneNumber;
        this.password = password;
    }

    @Exclude public void setId(String fieldOfficerId) {
        this.id = fieldOfficerId;
    }

    @Exclude public String getId() {
        return id;
    }

    public String getfOName() {
        return fOName;
    }

    public void setfOName(String fOName) {
        this.fOName = fOName;
    }

    public String getfOaddress() {
        return fOaddress;
    }

    public void setfOaddress(String fOaddress) {
        this.fOaddress = fOaddress;
    }

    public String getfOemailAddress() {
        return fOemailAddress;
    }

    public void setfOemailAddress(String fOemailAddress) {
        this.fOemailAddress = fOemailAddress;
    }

    public String getfOprofileImageUrl() {
        return fOprofileImageUrl;
    }

    public void setfOprofileImageUrl(String fOprofileImageUrl) {
        this.fOprofileImageUrl = fOprofileImageUrl;
    }

    public String getfOphoneNumber() {
        return fOphoneNumber;
    }

    public void setfOphoneNumber(String fOphoneNumber) {
        this.fOphoneNumber = fOphoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
