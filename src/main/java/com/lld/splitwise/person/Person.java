package com.lld.splitwise.person;


import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.expenseGroup.ExpenseGroup;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String email;

    public List<Expense> getPaidExpenses() {
        return paidExpenses;
    }

    public void setPaidExpenses(List<Expense> paidExpenses) {
        this.paidExpenses = paidExpenses;
    }

    @OneToMany(mappedBy = "paidByPerson")
    private List<Expense> paidExpenses;


    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="personGroupConjunction",
    joinColumns = @JoinColumn(name = "personId",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="expenseGroupId",referencedColumnName = "id"))
    private List<ExpenseGroup> groups;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
