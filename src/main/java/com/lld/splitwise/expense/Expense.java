package com.lld.splitwise.expense;

import com.lld.splitwise.person.Person;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Expense {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Double amount;

    @ManyToOne()
    @JoinColumn(name = "paidPersonId")
    private Person paidByPerson;

    private Date date;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Person getPaidByUser() {
        return paidByPerson;
    }

    public void setPaidByUser(Person paidByPerson) {
        this.paidByPerson = paidByPerson;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
