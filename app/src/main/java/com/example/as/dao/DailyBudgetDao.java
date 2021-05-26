package com.example.as.dao;

import com.example.as.entity.Budget;
import com.example.as.entity.DailyBudget;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public interface DailyBudgetDao {
    public boolean insert(DailyBudget budget);
    public boolean deleteByDate(int year,int month,int day,String accountId);
    public boolean updateAmount(int budgetId,double amount);
    public boolean updateBalance(int budgetId,double balance);
    public boolean updateDay(int budgetId, int year,int month,int day);
    public ArrayList<DailyBudget> findByAccountId(String accountId);
    public DailyBudget findByDay(int year,int month,int day,String accountId);
    public DailyBudget findByBudgetId(Integer budgetId, String accountId);
    public ArrayList<DailyBudget> findByMonth(int year,int month,String accountId);
}
