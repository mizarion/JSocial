package com.mizarion.jsocial.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Email required")
    private String email;

    @NotBlank(message = "Username required")
    @JsonAlias(value={"name", "nickname"})
    private String username;

    @NotBlank(message = "Password required")
    private String password;
}
