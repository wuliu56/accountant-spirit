package com.example.as.dao;

import com.example.as.entity.Budget;

import java.util.List;

public interface BudgetDao {
    public boolean insert(Budget budget);
    public boolean deleteByBudgetId(String budgetId);
    public boolean updateAmount(Budget budget,double amount);
    public boolean updateBalance(Budget budget,double balance);
    public List<Budget> findByAccountId(String accountId);
    public List<Budget> findByBudgetId(String budgetId);
}
