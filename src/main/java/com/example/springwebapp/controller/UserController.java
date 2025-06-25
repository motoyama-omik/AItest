package com.example.springwebapp.controller;

import com.example.springwebapp.dto.BatchUserCreateDto;
import com.example.springwebapp.dto.UserCreateDto;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("userCount", userService.getUserCount());
        return "users/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("userCreateDto", new UserCreateDto());
        return "users/create";
    }
    
    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute("userCreateDto") UserCreateDto userCreateDto, 
                           BindingResult result, 
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/create";
        }
        
        // パスワード確認
        if (!userCreateDto.isPasswordMatch()) {
            result.rejectValue("confirmPassword", "error.userCreateDto", "パスワードが一致しません");
            return "users/create";
        }
        
        // メールアドレスの重複チェック
        if (userService.existsByEmail(userCreateDto.getEmail())) {
            result.rejectValue("email", "error.userCreateDto", "このメールアドレスは既に使用されています");
            return "users/create";
        }
        
        // 確認画面に遷移
        model.addAttribute("userCreateDto", userCreateDto);
        return "users/confirm";
    }
    
    @PostMapping("/confirm")
    public String confirmUser(@ModelAttribute("userCreateDto") UserCreateDto userCreateDto,
                            RedirectAttributes redirectAttributes) {
        
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());
        
        User savedUser = userService.saveUser(user);
        
        redirectAttributes.addFlashAttribute("message", "ユーザーが正常に作成されました");
        redirectAttributes.addFlashAttribute("createdUser", savedUser);
        return "redirect:/users";
    }
    
    @GetMapping("/batch")
    public String showBatchCreateForm(Model model) {
        model.addAttribute("batchUserCreateDto", new BatchUserCreateDto());
        return "users/batch-create";
    }
    
    @PostMapping("/batch")
    public String createUsersBatch(@Valid @ModelAttribute("batchUserCreateDto") BatchUserCreateDto batchUserCreateDto,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/batch-create";
        }
        
        // CSV形式の文字列をパースしてユーザーリストを作成
        List<User> users = parseUserList(batchUserCreateDto.getUserList());
        
        if (users.isEmpty()) {
            result.rejectValue("userList", "error.batchUserCreateDto", "有効なユーザーデータが見つかりません");
            return "users/batch-create";
        }
        
        // 重複チェック
        List<String> duplicateEmails = users.stream()
                .filter(user -> userService.existsByEmail(user.getEmail()))
                .map(User::getEmail)
                .collect(Collectors.toList());
        
        if (!duplicateEmails.isEmpty()) {
            result.rejectValue("userList", "error.batchUserCreateDto", 
                    "以下のメールアドレスは既に使用されています: " + String.join(", ", duplicateEmails));
            return "users/batch-create";
        }
        
        List<User> savedUsers = userService.createUsersBatch(users);
        
        redirectAttributes.addFlashAttribute("message", 
                savedUsers.size() + "人のユーザーが正常に作成されました");
        redirectAttributes.addFlashAttribute("createdUsers", savedUsers);
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
    
    @GetMapping("/search")
    public String searchUsers(@RequestParam(required = false) String name,
                            @RequestParam(required = false) String email,
                            Model model) {
        List<User> users;
        
        if (name != null && !name.trim().isEmpty()) {
            users = userService.findUsersByNameContaining(name.trim());
        } else if (email != null && !email.trim().isEmpty()) {
            users = userService.findUsersByEmailContaining(email.trim());
        } else {
            users = userService.getAllUsers();
        }
        
        model.addAttribute("users", users);
        model.addAttribute("searchName", name);
        model.addAttribute("searchEmail", email);
        return "users/list";
    }
    
    @GetMapping("/{id}")
    public String showUserDetail(@PathVariable Long id, Model model) {
        return userService.getUserById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "users/detail";
                })
                .orElse("redirect:/users");
    }
    
    /**
     * CSV形式の文字列をパースしてユーザーリストを作成
     */
    private List<User> parseUserList(String userList) {
        return userList.lines()
                .filter(line -> !line.trim().isEmpty())
                .map(line -> {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        User user = new User();
                        user.setName(parts[0].trim());
                        user.setEmail(parts[1].trim());
                        user.setPassword(parts[2].trim());
                        return user;
                    }
                    return null;
                })
                .filter(user -> user != null && 
                        user.getName() != null && !user.getName().isEmpty() &&
                        user.getEmail() != null && !user.getEmail().isEmpty() &&
                        user.getPassword() != null && !user.getPassword().isEmpty())
                .collect(Collectors.toList());
    }
} 