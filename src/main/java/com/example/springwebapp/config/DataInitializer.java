package com.example.springwebapp.config;

import com.example.springwebapp.entity.User;
import com.example.springwebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // データベースが空の場合のみ初期データを挿入
        if (userRepository.count() == 0) {
            User user1 = new User("田中太郎", "tanaka@example.com", "password123");
            User user2 = new User("佐藤花子", "sato@example.com", "password456");
            User user3 = new User("鈴木一郎", "suzuki@example.com", "password789");
            User user4 = new User("高橋美咲", "takahashi@example.com", "password012");
            User user5 = new User("伊藤健太", "ito@example.com", "password345");

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);

            System.out.println("初期データが正常に挿入されました。");
        }
    }
} 