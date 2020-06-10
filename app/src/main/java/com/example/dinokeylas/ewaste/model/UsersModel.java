package com.example.dinokeylas.ewaste.model;

public class UsersModel {

    private String userName, fullName, emailAddress, address, profileImageUrl, phoneNumber, password;

    public UsersModel(){

    }

    public UsersModel(String userName, String fullName, String emailAddress, String address, String profileImageUrl, String phoneNumber, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.profileImageUrl = profileImageUrl;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
