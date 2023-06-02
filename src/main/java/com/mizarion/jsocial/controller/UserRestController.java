package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.UserDto;
import com.mizarion.jsocial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * Выводит список существующих пользователей
     */
    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

}
