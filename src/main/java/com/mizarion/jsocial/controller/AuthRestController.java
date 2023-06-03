package com.mizarion.jsocial.controller;

import com.mizarion.jsocial.model.dto.JwtDto;
import com.mizarion.jsocial.model.dto.LoginDto;
import com.mizarion.jsocial.model.dto.UserDto;
import com.mizarion.jsocial.security.jwt.JWTGenerator;
import com.mizarion.jsocial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;

    /**
     * Создает нового пользователя
     */
    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto registerDto) {
        registerDto.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        userService.addUser(registerDto);
        return ResponseEntity.ok("register user " + registerDto.getUsername());
    }

    /**
     *
     */
    @PostMapping("login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new JwtDto(token), HttpStatus.OK);
    }

}
