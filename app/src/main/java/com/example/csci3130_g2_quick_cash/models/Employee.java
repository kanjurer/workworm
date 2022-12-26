package com.example.csci3130_g2_quick_cash.models;

public class Employee {
    private String fullName;
    private String email;
    private String contact;
    private Preference preference;
    private int rating;


    public Employee(String fullName, String email, String contact) {
        this.fullName = fullName;
        this.email = email;
        this.contact = contact;
        this.preference = null;
        this.rating = 5;
    }

    public Employee(){}


    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }


    public Preference getPreference() {
        return preference;
    }


    public void setRating(int rating) {
        this.rating = rating;
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


    public void setPreference(Preference preference) {
        this.preference = preference;
    }

}
