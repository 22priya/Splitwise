package com.lld.splitwise.person;

import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.expense.ExpenseService;
import com.lld.splitwise.expenseGroup.ExpenseGroup;
import com.lld.splitwise.expenseGroup.ExpenseGroupRepository;
import com.lld.splitwise.split.Split;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ExpenseGroupRepository groupRepository;

    @Autowired
    private ExpenseService expenseService;


    public List<Person> getAllUsers(){
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) throws Exception{
        Optional<Person> person= personRepository.findById(id);
        if(person.isPresent())
            return person.get();
        else
            throw new Exception("Person not found with id "+id);
    }

    public Person createPerson(Person person){
        return this.personRepository.save(person);
    }

    public List<Person> getAllPersonsByGroup(Long id) throws Exception {
        Optional<ExpenseGroup> group=this.groupRepository.findById(id);
        if(group.isEmpty())
            throw new Exception("There is no group with id: "+id);
        else
            return group.get().getMembers();
    }

    public Map<Person,Double> getAllAmountsThatOtherPersonsLentCurrentPerson(Long personId) throws Exception {
      Optional<Person> person=personRepository.findById(personId);
      if(person.isEmpty())
          throw new Exception("No Person exist with id: "+personId);
      Map<Person,Double> result=new HashMap<>();
        List<Person> persons=personRepository.findAll();
        for (Person p:persons
             ) {
            if(p.getId()!=personId)
                result.put(p,getAmountPerson1LentToPerson2(p,person.get()));
        }
        return result;
    }

    public Double getAmountPerson1LentToPerson2(Person person1,Person person2) throws Exception {
        //if returned value is positive -> person1 lent to person2
        //if return value is negative -> person2 lent to person1

        List<Expense> expensesPaidByPerson1=person1.getPaidExpenses();
        List<Expense> expensesPaidByPerson2=person2.getPaidExpenses();

        List<Split> splitsOfPerson2ThatArePaidByPerson1=getSplitsOfPerson(expensesPaidByPerson1, person2.getId());

        List<Split> splitsOfPerson1ThatArePaidByPerson2= getSplitsOfPerson(expensesPaidByPerson2, person1.getId());

        Double amountP1LentToP2 = getSumOfAllSplits(splitsOfPerson2ThatArePaidByPerson1);

        Double amountP2LentToP1 = getSumOfAllSplits(splitsOfPerson1ThatArePaidByPerson2);

        return amountP1LentToP2 - amountP2LentToP1;
    }

    Double getSumOfAllSplits(List<Split> splits)
    {
        return splits
                .stream()
                .map(it->it.getAmount())
                .reduce(0.0,(i,j)->i+j);
    }

    List<Split> getSplitsOfPerson(List<Expense> expenses,Long personId)
    {
        return expenses
                .stream()
                .flatMap(it->it.getSplits().stream())
                .filter(it->it.getPerson().getId().equals(personId))
                .collect(Collectors.toList());
    }

}
