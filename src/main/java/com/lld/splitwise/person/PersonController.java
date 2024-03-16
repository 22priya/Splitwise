package com.lld.splitwise.person;

import com.lld.splitwise.expenseGroup.ExpenseGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public Person createPerson(@RequestBody Person person ){
        return this.personService.createPerson(person);
    }

    @GetMapping
    public List<Person> getAllPersons(){
        return this.personService.getAllUsers();
    }

    @GetMapping(path = "/{groupId}")
    public List<Person> getAllMembersOfGroup(@PathVariable Long groupId) throws Exception {
        return this.personService.getAllPersonsByGroup(groupId);
    }
}
