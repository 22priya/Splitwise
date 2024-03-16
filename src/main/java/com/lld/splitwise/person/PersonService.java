package com.lld.splitwise.person;

import com.lld.splitwise.expenseGroup.ExpenseGroup;
import com.lld.splitwise.expenseGroup.ExpenseGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ExpenseGroupRepository groupRepository;


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
}
