package com.lld.splitwise.splitStrategy;

import com.lld.splitwise.expense.Expense;

public interface SplitStrategy {
    void getSplits(Expense expense);

    boolean isValidSplitAmount(Expense expense);
}
