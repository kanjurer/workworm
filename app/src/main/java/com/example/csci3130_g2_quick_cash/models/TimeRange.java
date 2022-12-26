package com.example.csci3130_g2_quick_cash.models;

import java.io.Serializable;

public class TimeRange implements Serializable {
    private String minDate;
    private String maxDate;
    private String minDuration;
    private String maxDuration;

    public TimeRange() {}

    public TimeRange(String minDate, String maxDate, String minDuration, String maxDuration) {
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public String getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(String minDuration) {
        this.minDuration = minDuration;
    }

    public String getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(String maxDuration) {
        this.maxDuration = maxDuration;
    }
}
