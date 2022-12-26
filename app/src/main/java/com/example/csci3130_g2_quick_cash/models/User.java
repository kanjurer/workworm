package com.example.csci3130_g2_quick_cash.models;

public class User {
    private String fullName;
    private String email;
    private String contact;
    private String password;
    private Preference preference;
    private CreditCard creditCard;
    private int rating;
    private int totalCompletedJobs;
    private int totalIncome;
    private int totalClosedJobs;


    public User(String fullName, String email, String password, String contact, CreditCard creditCard) {
        this.fullName = fullName;
        this.email = email;
        this.contact = contact;
        this.creditCard = creditCard;
        this.password = password;
        this.preference = null;
        this.rating = 5;
        this.totalCompletedJobs = 0;
        this.totalIncome = 0;
        this.totalClosedJobs = 0;
    }

    public User() {
    }

    public int getTotalClosedJobs() {
        return totalClosedJobs;
    }

    public void setTotalClosedJobs(int totalClosedJobs) {
        this.totalClosedJobs = totalClosedJobs;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public Preference getPreference() {
        return preference;
    }

    public int getTotalCompletedJobs() {
        return totalCompletedJobs;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTotalCompletedJobs(int totalCompletedJobs) {
        this.totalCompletedJobs = totalCompletedJobs;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getRating() {
        return rating;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public boolean isVerified() {
        return !creditCard.getCardNum().equals("");
    }

    public Employee toEmployee(){
        return new Employee(this.fullName,this.email, this.contact);
    }

}
