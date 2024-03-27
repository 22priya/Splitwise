package com.lld.splitwise.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.expenseGroup.ExpenseGroup;
import com.lld.splitwise.split.Split;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "paidByPerson", cascade = CascadeType.ALL)
    //@JoinColumn annotation shows that this is owning side of relationship
    private List<Expense> paidExpenses;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name="personGroupConjunction",
    joinColumns = @JoinColumn(name = "personId",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="expenseGroupId",referencedColumnName = "id"))
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

    @JsonIgnore
    public List<Expense> getPaidExpenses() {
        return paidExpenses;
    }

    public void setPaidExpenses(List<Expense> paidExpenses) {
        this.paidExpenses = paidExpenses;
    }

    @JsonIgnore
    public List<ExpenseGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ExpenseGroup> groups) {
        this.groups = groups;
    }

    @JsonIgnore
    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
