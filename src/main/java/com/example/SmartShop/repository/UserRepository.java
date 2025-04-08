package com.example.SmartShop.repository;

import com.example.SmartShop.entity.User;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByNumberPhone(@Size(min = 10) Integer numberPhone);
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserId(Integer userId);
}
