package com.lld.splitwise.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;
//    public List<Expense> getAllExpensesByUser(Integer userId)
//    {
//        return repository.find
//    }
}
