package com.lld.splitwise.expenseGroup;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.person.Person;

import java.util.Date;
import java.util.List;

@Entity
public class ExpenseGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date createdDate;

    private Boolean active;

    @OneToMany(mappedBy = "expenseGroup",cascade = CascadeType.ALL)
    private List<Expense> expenses;

    @ManyToMany(mappedBy = "groups")
    private List<Person> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonIgnore
    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }
}
