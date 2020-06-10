package com.example.dinokeylas.ewaste.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class InformationModel implements Serializable{
    @Exclude
    private String informationId;
    private String iTittle, iContent, iAuthor, iInfoImageUrl;
    private Date iDate;


    public InformationModel() {

    }

    public InformationModel(String iTittle, String iContent, String iAuthor, String iInfoImageUrl, Date iDate) {
        this.iTittle = iTittle;
        this.iContent = iContent;
        this.iAuthor = iAuthor;
        this.iInfoImageUrl = iInfoImageUrl;
        this.iDate = iDate;
    }

    @Exclude
    public String getInformationId() {
        return informationId;
    }

    @Exclude
    public void setInformationId(String informationId) {
        this.informationId = informationId;
    }

    public String getiTittle() {
        return iTittle;
    }

    public String getiContent() {
        return iContent;
    }

    public String getiAuthor() {
        return iAuthor;
    }

    public String getiInfoImageUrl() { return iInfoImageUrl; }

    public Date getiDate() {
        return iDate;
    }
}
