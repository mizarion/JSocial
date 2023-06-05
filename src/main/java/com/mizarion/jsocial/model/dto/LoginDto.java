package com.mizarion.jsocial.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @ApiModelProperty(example = "user1", value = "Username of the user", required = true)
    @NotBlank(message = "Username required")
    @JsonAlias(value = {"name", "nickname"})
    private String username;

    @ApiModelProperty(example = "password", value = "Password of the user", required = true)
    @NotBlank(message = "Password required")
    private String password;
}
