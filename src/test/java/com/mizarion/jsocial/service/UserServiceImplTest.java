package com.mizarion.jsocial.service;

import com.mizarion.jsocial.model.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceImplTest {

    @Autowired
    private UserService service;
    private final static UserDto dto = new UserDto("test1@mail.com", "name", "password");

    @Test
    void addUser() {
        service.addUser(dto);
    }

    @Test
    void getEmptyUsers() {
        Assertions.assertEquals(0, service.getUsers().size());
    }

    @Test
    void getOneUser() {
        service.addUser(dto);
        Assertions.assertEquals(1, service.getUsers().size());
    }

    @Test
    void addTwiceGetOneUser() {
        service.addUser(dto);
        Assertions.assertThrows(Exception.class, () -> service.addUser(dto));
        Assertions.assertEquals(1, service.getUsers().size());
    }
}