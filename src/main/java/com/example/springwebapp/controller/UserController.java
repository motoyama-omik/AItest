package com.example.springwebapp.controller;

import com.example.springwebapp.entity.User;
import com.example.springwebapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }
    
    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") User user, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }
        
        if (userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "このメールアドレスは既に使用されています");
            return "users/form";
        }
        
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "ユーザーが正常に作成されました");
        return "redirect:/users";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return userService.getUserById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "users/form";
                })
                .orElse("redirect:/users");
    }
    
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, 
                           @Valid @ModelAttribute("user") User user, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }
        
        user.setId(id);
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "ユーザーが正常に更新されました");
        return "redirect:/users";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "ユーザーが正常に削除されました");
        return "redirect:/users";
    }
} 