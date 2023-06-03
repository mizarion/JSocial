package com.mizarion.jsocial.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePostDto {

    @NotBlank(message = "ID required")
    private Long id;
    @NotBlank(message = "Owner required")
    private String owner;
}
