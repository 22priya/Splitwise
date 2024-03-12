package com.lld.splitwise.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;


    public List<Person> getAllUsers(){
        return repository.findAll();
    }

    public Person getPersonById(Integer id) throws Exception{
        Optional<Person> person=repository.findById(id);
        if(person.isPresent())
            return person.get();
        else
            throw new Exception("Person not found with id "+id);
    }
}
