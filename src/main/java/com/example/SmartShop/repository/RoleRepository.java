package com.example.SmartShop.repository;

import com.example.SmartShop.entity.Role;
import com.example.SmartShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
