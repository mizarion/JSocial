package com.mizarion.jsocial.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {

    @NotBlank(message = "Title required")
    private String title;
    @NotBlank(message = "Text required")
    private String text;
}
