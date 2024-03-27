package com.lld.splitwise.split;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.person.Person;

import javax.persistence.*;

@Entity
public class Split {
    @Id
    @GeneratedValue
    private Integer id;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "expenseId")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name="personId")
    private Person person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonIgnore
    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Split{" +
                "id=" + id +
                ", amount=" + amount +
                ", expense=" + expense +
                ", person=" + person +
                '}';
    }
}
