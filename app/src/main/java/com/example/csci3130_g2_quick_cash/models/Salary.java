package com.example.csci3130_g2_quick_cash.models;

import java.io.Serializable;

public class Salary implements Serializable {
    private String minSalary;
    private String maxSalary;

    public Salary() {}

    public Salary(String minSalary, String maxSalary) {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }
}
