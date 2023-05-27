package com.example.debt_be.repository;

import com.example.debt_be.entity.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepo extends JpaRepository<Debt, Integer> {
}
