package com.lld.splitwise.splitStrategy;

import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.person.Person;
import com.lld.splitwise.split.Split;

import java.util.HashMap;
import java.util.List;

public interface SplitStrategy {
    List<Split> getSplits(Expense expense);
}
