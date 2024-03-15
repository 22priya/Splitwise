package com.lld.splitwise.expenseGroup;

import com.lld.splitwise.person.Person;
import com.lld.splitwise.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseGroupService {

    @Autowired
    private ExpenseGroupRepository groupRepository;
    @Autowired
    private PersonRepository personRepository;

    public ExpenseGroup addGroup(ExpenseGroup group)
    {
        group.setActive(true);
        group.setCreatedDate(new Date());
        return this.groupRepository.save(group);
    }

    public ExpenseGroup addMemberToGroup(Long groupId,Long personId) throws Exception
    {
        Optional<ExpenseGroup> group=this.groupRepository.findById(groupId);
        Optional<Person> person=this.personRepository.findById(personId);
        if(group.isEmpty())
            throw new Exception("No Group exist with this id");
        if(person.isEmpty())
            throw new Exception("No Person exist with this id");
        group.get().getMembers().add(person.get());
        person.get().getGroups().add(group.get());
        this.personRepository.save(person.get());
        return this.groupRepository.save(group.get());
    }

    public List<ExpenseGroup> getAllExpenseGroups(){
        return this.groupRepository.findAll();
    }

    public List<ExpenseGroup> getAllGroupsByPerson(Long id){
        return this.personRepository.findById(id).get().getGroups();
    }

}
