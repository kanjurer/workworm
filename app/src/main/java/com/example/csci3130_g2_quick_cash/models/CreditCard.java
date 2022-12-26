package com.example.csci3130_g2_quick_cash.models;

public class CreditCard {
    private String cardNum;
    private String expiry;
    private String security;

    public CreditCard(String cardNum, String expiry, String security){
        this.cardNum = cardNum;
        this.expiry = expiry;
        this.security = security;
    }

    public CreditCard(){}

    public String getCardNum() {
        return cardNum;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
