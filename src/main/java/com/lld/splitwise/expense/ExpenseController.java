package com.lld.splitwise.expense;


import com.lld.splitwise.splitStrategy.SplitStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity addExpense(@RequestBody Expense expense, @RequestParam Long groupId, @RequestParam Long paidPersonId, @RequestParam SplitStrategies splitStrategy){
        try {
            Expense result=this.expenseService.addExpense(expense,paidPersonId,groupId,splitStrategy);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sharedExpense/{personId}")
    public List<Expense> getSharedExpenses(@PathVariable Long personId) throws Exception {
        return this.expenseService.getAllSharedExpensesByPerson(personId);
    }

    @GetMapping("/paidExpense/{personId}")
    public List<Expense> getAllExpensesPaidByPerson(@PathVariable Long personId) throws  Exception{
        return this.expenseService.getAllExpensesPaidByPerson(personId);
    }

    @GetMapping("/{groupId}")
    public List<Expense>getAllExpensesByGroup(@PathVariable Long groupId) throws Exception{
        return expenseService.getAllExpensesByGroup(groupId);
    }
}
