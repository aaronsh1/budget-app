package com.budget.api.repository;

import com.budget.api.model.BudgetEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetEntryRepository extends JpaRepository<BudgetEntry, Integer> {
}