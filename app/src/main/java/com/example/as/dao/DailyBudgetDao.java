package com.example.as.dao;

import com.example.as.entity.DailyBudget;

import java.sql.Date;
import java.util.ArrayList;

public interface DailyBudgetDao {
    public boolean insert(DailyBudget budget);
    public boolean deleteByBudgetId(String budgetId);
    public boolean updateAmount(String budgetId,double amount);
    public boolean updateBalance(String budgetId,double balance);
    public boolean updateDate(String budgetId, Date date);
    public ArrayList<DailyBudget> findByAccountId(String accountId);
    public DailyBudget findByBudgetId(String budgetId);
}
