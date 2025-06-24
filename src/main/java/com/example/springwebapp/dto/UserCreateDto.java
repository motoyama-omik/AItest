package com.example.springwebapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateDto {
    
    @NotBlank(message = "名前は必須です")
    @Size(min = 2, max = 50, message = "名前は2文字以上50文字以下で入力してください")
    private String name;
    
    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "有効なメールアドレスを入力してください")
    private String email;
    
    @NotBlank(message = "パスワードは必須です")
    @Size(min = 6, message = "パスワードは6文字以上で入力してください")
    private String password;
    
    @NotBlank(message = "パスワード確認は必須です")
    private String confirmPassword;
    
    // デフォルトコンストラクタ
    public UserCreateDto() {}
    
    // コンストラクタ
    public UserCreateDto(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
    
    // Getter and Setter
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    // パスワード確認メソッド
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
} 