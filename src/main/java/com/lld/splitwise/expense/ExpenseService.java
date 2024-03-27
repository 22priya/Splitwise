package com.lld.splitwise.expense;

import com.lld.splitwise.expenseGroup.ExpenseGroup;
import com.lld.splitwise.expenseGroup.ExpenseGroupRepository;
import com.lld.splitwise.person.Person;
import com.lld.splitwise.person.PersonRepository;
import com.lld.splitwise.split.Split;
import com.lld.splitwise.splitStrategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ExpenseGroupRepository expenseGroupRepository;
    @Autowired
    private EquallySplitStrategy equallySplitStrategy;
    @Autowired
    private ExactAmountSplitStrategy exactAmountSplitStrategy;
    @Autowired
    private ExactPercentSplitStrategy exactPercentSplitStrategy;

    public List<Expense> getAllExpensesSharedByPerson(Long personId) throws Exception{
        Optional<Person> person=this.personRepository.findById(personId);
        if(person.isEmpty())
            throw new Exception("There is no person with id: "+personId);
        else
            return person.get().getSplits().stream().flatMap(it-> Arrays.asList(it.getExpense()).stream()).collect(Collectors.toList());
    }

    public Expense addExpense(Expense expense,Long paidPersonId, Long groupId,SplitStrategies splitStrategy) throws Exception
    {   Optional<Person> person=this.personRepository.findById(paidPersonId);
        Optional<ExpenseGroup> group=this.expenseGroupRepository.findById(groupId);
        if(person.isEmpty())
            throw new Exception("There is no person with id: "+paidPersonId);
        else if(group.isEmpty())
            throw new Exception("There is no group with id: "+groupId);
        expense.setDate(new Date());
        expense.setPaidByPerson(person.get());
        expense.setExpenseGroup(group.get());
        if(!areValidSplitUsers(expense))
            throw new Exception("The expense is being shared by a user who is not added in groupId: "+groupId);
        List<Person> sharedPersons=new ArrayList<>();
        for(Split split :expense.getSplits())
        {
            Optional<Person> p=personRepository.findById(split.getPerson().getId());
            split.setPerson(p.get());
            sharedPersons.add(p.get());
        }

        SplitStrategy strategy=this.setSplitStrategy(splitStrategy);
        expense.setSplitStrategy(strategy);
        if(!strategy.isValidSplitAmount(expense))
            throw new Exception("The split inputs are not correct");
        expense.setSplits();
        if(this.isValidExpense(expense))
            return this.expenseRepository.save(expense);
        throw new Exception("This expense is not valid, cannot add it to DB");
    }

    private SplitStrategy setSplitStrategy(SplitStrategies strategyType) throws Exception {
        switch (strategyType) {
            case SPLIT_BY_EXACT_AMOUNT:
            {
                return this.exactAmountSplitStrategy;
            }
            case SPLIT_BY_EQUAL_AMOUNT:
            {
                return this.equallySplitStrategy;
            }
            case SPLIT_BY_EXACT_PERCENT:
            {
                return this.exactPercentSplitStrategy;
            }
        }
        throw new Exception("Wrong split strategy is sent");
    }

    private boolean isValidExpense(Expense expense)
    {
        List<Split> splits=expense.getSplits();
        Double sumOfSplits=splits.stream()
                                .mapToDouble(Split::getAmount)
                                .sum();
        return expense.getAmount().equals(sumOfSplits);
    }

    private boolean areValidSplitUsers(Expense expense)
    {
        List<Split> splits=expense.getSplits();
        Optional<ExpenseGroup> group=this.expenseGroupRepository.findById(expense.getExpenseGroup().getId());
        List<Person> groupPersons=group.get().getMembers();
        List<Person> splitPersons=splits.stream().map(it->it.getPerson()).toList();
        return groupPersons.containsAll(splitPersons);
    }

    public List<Expense> getAllExpensesByGroup(Long groupId) throws Exception {
        Optional<ExpenseGroup> group=this.expenseGroupRepository.findById(groupId);
        if(group.isEmpty())
            throw new Exception("No group exist with id: "+groupId);
        else
            return group.get().getExpenses();
    }

    public List<Expense> getAllExpensesPaidByPerson(Long personId) throws Exception {
        Optional<Person> person=this.personRepository.findById(personId);
        if(person.isEmpty())
            throw new Exception("No person exist with id: "+personId);
        else
            return person.get().getPaidExpenses();
    }

    public List<Expense> getAllSharedExpensesByPerson(Long personId) throws Exception{
        Optional<Person> person=this.personRepository.findById(personId);
        if(person.isEmpty())
            throw new Exception("No person exist with id: "+personId);
        else
            return person.get().getSplits().stream().map(it->it.getExpense()).collect(Collectors.toList());
    }

}
