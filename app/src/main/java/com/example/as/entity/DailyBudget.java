package com.example.as.entity;

import com.example.as.entity.Budget;

public class DailyBudget extends Budget {

    private double amount;
    private boolean ifOver;
    private int day;

    public int getDay() {
        return day;
    }

}
