package com.lld.splitwise.expenseGroup;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.lld.splitwise.person.Person;

import java.util.Date;
import java.util.List;

@Entity
public class ExpenseGroup {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Date createdDate;

    private Boolean active;

    @ManyToMany(mappedBy = "groups")
    private List<Person> members;
}
