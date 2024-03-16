package com.lld.splitwise.expenseGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/group")
public class ExpenseGroupController {

    @Autowired
    private ExpenseGroupService expenseGroupService;

    @PostMapping
    public ExpenseGroup addGroup(@RequestBody ExpenseGroup group)
    {
        return this.expenseGroupService.addGroup(group);
    }

    @PatchMapping(path="addMember/{groupId}/{personId}")
    public ResponseEntity addMemberToGroup(@PathVariable Long groupId, @PathVariable Long personId ){
        try
        {
            this.expenseGroupService.addMemberToGroup(groupId,personId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path="removeMember/{groupId}/{personId}")
    public ResponseEntity removeMemberFromGroup(@PathVariable Long groupId, @PathVariable Long personId ){
        try
        {
            this.expenseGroupService.removeMemberFromGroup(groupId,personId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<ExpenseGroup> getAllGroups(){
        return this.expenseGroupService.getAllExpenseGroups();
    }

    @GetMapping(path="/{id}")
    public List<ExpenseGroup> getAllGroupsByPersonId(@PathVariable Long id) throws Exception {
        return this.expenseGroupService.getAllGroupsByPerson(id);
    }
}
