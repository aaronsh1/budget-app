package com.budget.api.repository;

import com.budget.api.model.AccountEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEntryRepository extends JpaRepository<AccountEntry, Integer> {
}