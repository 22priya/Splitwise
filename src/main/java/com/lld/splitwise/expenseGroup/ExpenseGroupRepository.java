package com.lld.splitwise.expenseGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup,Long> {
}
