package com.mizarion.jsocial.model.dto;

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

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Password required")
    private String password;
}
