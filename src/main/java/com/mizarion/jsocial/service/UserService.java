package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.UserDto;

import java.util.List;

public interface UserService {

    void addUser(UserDto userDto);

    List<UserDto> getUsers();

}
