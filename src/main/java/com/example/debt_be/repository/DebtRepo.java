package com.example.debt_be.repository;

import com.example.debt_be.entity.model.Debt;
import com.example.debt_be.entity.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepo extends JpaRepository<Debt, Integer> {
    @Query("SELECT d FROM Debt d WHERE d.user.id = :userId")
    List<Debt> findByUserId(@Param("userId") Integer userId);
}
