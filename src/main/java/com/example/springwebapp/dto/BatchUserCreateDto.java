package com.example.springwebapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class BatchUserCreateDto {
    
    @NotBlank(message = "ユーザーリストは必須です")
    private String userList;
    
    private List<UserCreateDto> users;
    
    // デフォルトコンストラクタ
    public BatchUserCreateDto() {}
    
    // コンストラクタ
    public BatchUserCreateDto(String userList) {
        this.userList = userList;
    }
    
    // Getter and Setter
    public String getUserList() {
        return userList;
    }
    
    public void setUserList(String userList) {
        this.userList = userList;
    }
    
    public List<UserCreateDto> getUsers() {
        return users;
    }
    
    public void setUsers(List<UserCreateDto> users) {
        this.users = users;
    }
} 