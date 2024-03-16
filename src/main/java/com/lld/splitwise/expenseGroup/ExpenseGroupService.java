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
        Person personObject=person.get();
        List<Person> members=group.get().getMembers();
        if(members.contains(personObject))
            throw new Exception("This person is already added in the group");
        group.get().getMembers().add(person.get());
        person.get().getGroups().add(group.get());
        this.personRepository.save(person.get());
        return this.groupRepository.save(group.get());
    }

    public ExpenseGroup removeMemberFromGroup(Long groupId,Long personId) throws Exception
    {
        Optional<ExpenseGroup> group=this.groupRepository.findById(groupId);
        Optional<Person> person=this.personRepository.findById(personId);
        if(group.isEmpty())
            throw new Exception("No Group exist with this id");
        if(person.isEmpty())
            throw new Exception("No Person exist with this id");
        Person personObject=person.get();
        List<Person> members=group.get().getMembers();
        if(members.contains(personObject))
        {
            group.get().getMembers().remove(person.get());
            person.get().getGroups().remove(group.get());
            this.personRepository.save(person.get());
            return this.groupRepository.save(group.get());
        }
        else
        {
            throw new Exception("This person is not added in the group");
        }
    }

    public List<ExpenseGroup> getAllExpenseGroups(){
        return this.groupRepository.findAll();
    }

    public List<ExpenseGroup> getAllGroupsByPerson(Long id) throws Exception {
        Optional<Person> p=this.personRepository.findById(id);
        if(p.isEmpty())
            throw new Exception("There is no person with id: "+id);
        else
            return p.get().getGroups();
    }

}
