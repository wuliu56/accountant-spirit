package com.example.as;

import java.sql.Date;//年月日

public class DailyBudget extends Budget{

    private double amount;
    private boolean ifOver;
    private int day;

    public int getDay() {
        return day;
    }

}
