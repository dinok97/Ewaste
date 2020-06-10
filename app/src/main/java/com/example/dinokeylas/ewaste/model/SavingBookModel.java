package com.example.dinokeylas.ewaste.model;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class SavingBookModel {
    @Exclude private String tranId;
    private String userId, explanation;
    private Date date;
    private Double debit, credit, balance;

    public SavingBookModel() {

    }

    public SavingBookModel(String userId, String explanation, Date date, Double debit, Double credit, Double balance) {
        this.userId = userId;
        this.explanation = explanation;
        this.date = date;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
    }

    @Exclude
    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    @Exclude
    public String getTranId() {
        return tranId;
    }

    public String getUserId() {
        return userId;
    }

    public String getExplanation() {
        return explanation;
    }

    public Date getDate() {
        return date;
    }

    public Double getDebit() {
        return debit;
    }

    public Double getCredit() {
        return credit;
    }

    public Double getBalance() {
        return balance;
    }
}
