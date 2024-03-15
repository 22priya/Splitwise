package com.lld.splitwise.splitStrategy;

import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.person.Person;
import com.lld.splitwise.split.Split;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExactPercentSplitStrategy implements SplitStrategy{
    private Map<Person,Double> shares;

    public ExactPercentSplitStrategy(Map<Person, Double> shares) {
        this.shares = shares;
    }

    @Override
    public List<Split> getSplits(Expense expense) {
        List<Split> splits=new ArrayList<>();
        List<Person> persons=expense.getPersons();

        if(shares.size()< persons.size())
            throw new IllegalArgumentException("Please provide split percent of all persons");
        else if(shares.size()>persons.size())
            throw new IllegalArgumentException("You are providing split percent for more person");
        else if(!this.sumOfShares().equals(100.00))
            throw new IllegalArgumentException("The sum of all percents is not equal to 100");
        else {
            for(Person p: expense.getPersons())
            {
                Split split=new Split();
                split.setAmount(shares.get(p)*expense.getAmount()*0.01);
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
