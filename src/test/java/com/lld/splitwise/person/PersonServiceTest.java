package com.lld.splitwise.person;

import com.lld.splitwise.expense.Expense;
import com.lld.splitwise.expense.ExpenseService;
import com.lld.splitwise.expenseGroup.ExpenseGroupRepository;
import com.lld.splitwise.split.Split;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ExpenseGroupRepository groupRepository;

    @Mock
    private ExpenseService expenseService;

    public static Expense expense1,expense2;
    public static Split split1,split2,split3,split4,split5,split6;

    public static Person p1,p2,p3;
    @BeforeAll
    public static void beforeAll(){
        p1=new Person();
        p2=new Person();
        p3=new Person();
        p1.setName("priya");
        p1.setId(1l);
        p2.setName("aashi");
        p2.setId(2l);
        p3.setName("dimpi");
        p3.setId(3l);

        expense1=new Expense();
        expense1.setAmount(230.0);
        expense1.setName("Dinner");


        expense2=new Expense();
        expense2.setAmount(500.0);
        expense2.setName("Maid");

        split1=new Split();
        split1.setPerson(p1);
        split1.setAmount(80.0);

        split2=new Split();
        split2.setPerson(p2);
        split2.setAmount(70.0);

        split3=new Split();
        split3.setPerson(p3);
        split3.setAmount(80.0);


        split4=new Split();
        split4.setPerson(p1);
        split4.setAmount(500.0/3);

        split5=new Split();
        split5.setPerson(p2);
        split5.setAmount(500.0/3);

        split6=new Split();
        split6.setPerson(p3);
        split6.setAmount(500.0/3);
        expense2.setSplits(List.of(split4,split5,split6));
        expense2.setPaidByPerson(p2);
        p1.setPaidExpenses(List.of(expense1));
        p2.setPaidExpenses(List.of(expense2));
        p3.setPaidExpenses(List.of());
        expense1.setPaidByPerson(p1);
        expense1.setSplits(List.of(split1,split2,split3));
    }

    @Test
    public  void getSplitsOfPersonTest(){
        List<Expense> expenses =new ArrayList<>();
        expenses.add(expense1);
        expenses.add(expense2);
        List<Split>result=this.personService.getSplitsOfPerson(expenses,1l);
        assertArrayEquals(result.toArray(),List.of(split1,split4).toArray());
    }

    @Test
    public void getSumOfAllSplitsTest(){
        assertAll(
                ()->assertEquals(expense1.getAmount(),personService.getSumOfAllSplits(expense1.getSplits())),
                ()->assertEquals(expense2.getAmount(),personService.getSumOfAllSplits(expense2.getSplits()))
        );
    }

    @Test
    public void getAmountPerson1LentToPerson2Test(){
        assertAll(
                ()->assertEquals(-96.66,personService.getAmountPerson1LentToPerson2(p1,p2),0.01),
                ()->assertEquals(80,personService.getAmountPerson1LentToPerson2(p1,p3)),
                ()->assertEquals(166.66,personService.getAmountPerson1LentToPerson2(p2,p3),0.01)
        );
    }

    @Test
    public void getAllAmountsThatOtherPersonsLentCurrentPersonWhenPersonNotExistsTest() {
        when(personRepository.findById(p3.getId())).thenReturn(null);
        assertThrows( Exception.class,()->personService.getAllAmountsThatOtherPersonsLentCurrentPerson(p3.getId()));
    }

    @Test
    public void getAllAmountsThatOtherPersonsLentCurrentPersonWhenPersonExistsTest() throws Exception {
        when(personRepository.findById(p3.getId())).thenReturn(Optional.of(p3));
        when(personRepository.findAll()).thenReturn(List.of(p1,p2,p3));
        Map<Person,Double> map=personService.getAllAmountsThatOtherPersonsLentCurrentPerson(p3.getId());
        assertEquals(80,map.get(p1));
        assertEquals(166.66,map.get(p2),0.01);
    }

}
