package com.lld.splitwise.person;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.expenseGroup.ExpenseGroup;
import com.lld.splitwise.split.Split;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "paidByPerson",cascade = CascadeType.ALL)
    private List<Expense> paidExpenses;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="personExpenseConjunction",
    joinColumns = @JoinColumn(name="personId",referencedColumnName = "id"),
    inverseJoinColumns  =@JoinColumn(name="expenseId",referencedColumnName = "id"))
    private List<Expense> sharedExpenses;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="personGroupConjunction",
    joinColumns = @JoinColumn(name = "personId",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="expenseGroupId",referencedColumnName = "id"))
    @JsonManagedReference
    private List<ExpenseGroup> groups;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "person")
    private List<Split> splits;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Expense> getPaidExpenses() {
        return paidExpenses;
    }

    public void setPaidExpenses(List<Expense> paidExpenses) {
        this.paidExpenses = paidExpenses;
    }

    public List<ExpenseGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ExpenseGroup> groups) {
        this.groups = groups;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    public List<Expense> getSharedExpenses() {
        return sharedExpenses;
    }

    public void setSharedExpenses(List<Expense> sharedExpenses) {
        this.sharedExpenses = sharedExpenses;
    }
}
