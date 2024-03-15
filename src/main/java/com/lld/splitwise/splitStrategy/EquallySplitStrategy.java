package com.lld.splitwise.splitStrategy;

import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.person.Person;
import com.lld.splitwise.split.Split;

import java.util.ArrayList;
import java.util.List;

public class EquallySplitStrategy implements SplitStrategy{
    @Override
    public List<Split> getSplits(Expense expense) {
        Double share=expense.getAmount() / expense.getPersons().size();
        List<Split> splits=new ArrayList<>();
        for(Person p: expense.getPersons())
        {
            Split split=new Split();
            split.setAmount(share);
            split.setPerson(p);
            split.setExpense(expense);
            splits.add(split);
        }
        return splits;
    }
}
