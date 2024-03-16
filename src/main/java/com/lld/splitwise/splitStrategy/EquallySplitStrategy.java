package com.lld.splitwise.splitStrategy;

import com.lld.splitwise.expense.Expense;
import org.springframework.stereotype.Component;

@Component
public class EquallySplitStrategy implements SplitStrategy{
    @Override
    public void getSplits(Expense expense) {
        Double share=expense.getAmount() / expense.getSplits().size();
        expense.getSplits().stream().forEach(it->{
            it.setAmount(share);
            it.setExpense(expense);
        });
    }

    @Override
    public boolean isValidSplitAmount(Expense expense) {
        return true;
    }
}
