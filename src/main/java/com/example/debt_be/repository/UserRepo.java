package com.example.debt_be.repository;

import com.example.debt_be.entity.model.UserModel;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo  extends JpaRepository<UserModel, Integer> {
    List<UserModel> findByMst (String mst);
    Optional<List<UserModel>> findByMstContainingOrNameContaining(@Nullable String mst, String name);

    UserModel findUserModelByMst(String mst);
}
