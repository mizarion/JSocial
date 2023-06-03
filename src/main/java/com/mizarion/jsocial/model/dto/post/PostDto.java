package com.mizarion.jsocial.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;
    private String owner;
    @NotBlank(message = "Title required")
    private String title;
    @NotBlank(message = "Text required")
    private String text;

    public PostDto(String owner, String title, String text) {
        this.owner = owner;
        this.title = title;
        this.text = text;
    }

}
