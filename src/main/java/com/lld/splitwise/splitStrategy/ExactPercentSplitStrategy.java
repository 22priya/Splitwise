package com.lld.splitwise.splitStrategy;

import com.lld.splitwise.expense.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExactPercentSplitStrategy implements SplitStrategy{

    @Override
    public void getSplits(Expense expense) {
        expense.getSplits().stream().forEach(it->{
            it.setAmount(it.getAmount()*expense.getAmount()*0.01);
            it.setExpense(expense);
        });
    }

    @Override
    public boolean isValidSplitAmount(Expense expense) {
        Double sumOfIndividualPercents=
                expense.getSplits().stream().mapToDouble(it->it.getAmount()).reduce(0,(i,j)->i+j);
        return sumOfIndividualPercents.equals(100.0);
    }

}
