package com.example.dinokeylas.ewaste.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class MessageModel implements Serializable {

    @Exclude
    private String messageId;
    private String sender, receiver, message;
    private Date sendDate, pickDate;
    private Boolean read;

    public MessageModel () {

    }

    public MessageModel(String sender, String receiver, String message, Date sendDate, Date pickDate, Boolean read) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sendDate = sendDate;
        this.pickDate = pickDate;
        this.read = read;
    }

    @Exclude
    public String getMessageId() {
        return messageId;
    }

    @Exclude
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public Date getPickDate() {
        return pickDate;
    }

    public Boolean getRead() {
        return read;
    }

}
