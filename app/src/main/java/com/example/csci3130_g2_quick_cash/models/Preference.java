package com.example.csci3130_g2_quick_cash.models;

import com.example.csci3130_g2_quick_cash.utils.PreferredPostValidation;

import java.io.Serializable;

public class Preference implements Serializable {

    private String preferenceTitle;
    private TimeRange preferenceTimeRange;
    private Salary preferenceSalary;
    private String preferencePlace;
    private boolean urgent;

    public Preference(String preferenceTitle, TimeRange preferenceTimeRange, Salary preferenceSalary,
                      String preferencePlace, boolean urgent) {
        this.preferenceTitle = preferenceTitle;
        this.preferenceTimeRange = preferenceTimeRange;
        this.preferenceSalary = preferenceSalary;
        this.preferencePlace = preferencePlace;
        this.urgent = urgent;
    }

    public Preference() {
    }


    public String getPreferenceTitle() {
        return preferenceTitle;
    }

    public void setPreferenceTitle(String preferenceTitle) {
        this.preferenceTitle = preferenceTitle;
    }

    public TimeRange getPreferenceTimeRange() {
        return preferenceTimeRange;
    }

    public void setPreferenceTimeRange(TimeRange preferenceTimeRange) {
        this.preferenceTimeRange = preferenceTimeRange;
    }

    public Salary getPreferenceSalary() {
        return preferenceSalary;
    }

    public void setPreferenceSalary(Salary preferenceSalary) {
        this.preferenceSalary = preferenceSalary;
    }

    public String getPreferencePlace() {
        return preferencePlace;
    }

    public void setPreferencePlace(String preferencePlace) {
        this.preferencePlace = preferencePlace;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    // preference matching scheme
    public boolean preferenceMatch(Post p) {
        if (p == null) return false;

        return PreferredPostValidation.checkIfPreferred(this, p);
    }
}
