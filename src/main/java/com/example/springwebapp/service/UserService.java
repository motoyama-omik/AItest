package com.example.springwebapp.service;

import com.example.springwebapp.entity.User;
import com.example.springwebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * 複数のユーザーを一括作成
     */
    public List<User> createUsersBatch(List<User> users) {
        return userRepository.saveAll(users);
    }
    
    /**
     * ユーザー数を取得
     */
    public long getUserCount() {
        return userRepository.count();
    }
    
    /**
     * メールアドレスでユーザーを検索（部分一致）
     */
    public List<User> findUsersByEmailContaining(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }
    
    /**
     * 名前でユーザーを検索（部分一致）
     */
    public List<User> findUsersByNameContaining(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
} 