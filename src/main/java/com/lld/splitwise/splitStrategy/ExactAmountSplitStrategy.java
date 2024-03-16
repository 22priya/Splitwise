package com.lld.splitwise.splitStrategy;

import com.lld.splitwise.expense.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExactAmountSplitStrategy implements SplitStrategy{

    @Override
    public void getSplits(Expense expense) {
        expense.getSplits().stream().forEach(it->{
            it.setExpense(expense);
        });
    }

    @Override
    public boolean isValidSplitAmount(Expense expense) {
        Double sumOfIndividualAmounts=
        expense.getSplits().stream().mapToDouble(it->it.getAmount()).reduce(0,(i,j)->i+j);
        return sumOfIndividualAmounts.equals(expense.getAmount());
    }
}
