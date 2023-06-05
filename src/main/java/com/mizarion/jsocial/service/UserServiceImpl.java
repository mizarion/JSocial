package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.UserDto;
import com.mizarion.jsocial.model.entity.UserEntity;
import com.mizarion.jsocial.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addUser(UserDto dto) {
        userRepository.save(modelMapper.map(dto, UserEntity.class));
    }

    @Override
    public List<UserDto> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(e -> modelMapper.map(e, UserDto.class))
                .toList();
    }

}
