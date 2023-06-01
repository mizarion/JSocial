package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.UserDto;
import com.mizarion.jsocial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * Создает нового пользователя
     */
    @PostMapping("/user")
    public String addUser(@Valid @RequestBody UserDto user) {
        userService.addUser(user);
        return "hello";
    }

    /**
     * Выводит список существующих пользователей
     */
    @GetMapping("/users")
    public List<UserDto> getUsers() {
       return userService.getUsers();
    }

}
