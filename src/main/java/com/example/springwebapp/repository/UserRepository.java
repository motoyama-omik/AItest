package com.example.springwebapp.repository;

import com.example.springwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<User> findByEmailContainingIgnoreCase(String email);
    
    List<User> findByNameContainingIgnoreCase(String name);
} 