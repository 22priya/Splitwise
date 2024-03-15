package com.lld.splitwise.splitStrategy;

import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.person.Person;
import com.lld.splitwise.split.Split;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

public class ExactAmountSplitStrategy implements SplitStrategy{

    private Map<Person,Double> shares;

    public ExactAmountSplitStrategy(Map<Person, Double> shares) {
        this.shares = shares;
    }

    @Override
    public List<Split> getSplits(Expense expense) {
        List<Split> splits=new ArrayList<>();
        List<Person> persons=expense.getPersons();
        if(shares.size()< persons.size())
            throw new IllegalArgumentException("Please provide split amounts of all persons");
        else if(shares.size()>persons.size())
            throw new IllegalArgumentException("You are providing split amounts for more person");
        else if(!this.sumOfShares().equals(expense.getAmount()))
            throw new IllegalArgumentException("The sum of shares not equals to expense amount");
        else {
            for(Person p: expense.getPersons())
            {
                Split split=new Split();
                split.setAmount(shares.get(p));
                split.setPerson(p);
                split.setExpense(expense);
                splits.add(split);
            }
            return splits;
        }
    }

    private Double sumOfShares(){
        return shares.values().stream().reduce(0.0,(i,j)->i+j);
    }
}
