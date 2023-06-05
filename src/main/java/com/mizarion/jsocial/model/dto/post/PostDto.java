package com.mizarion.jsocial.model.dto.post;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @ApiModelProperty(example = "1", value = "ID of the post")
    private Long id;

    @ApiModelProperty(example = "user1", value = "Owner of the post")
    @JsonAlias(value = {"name", "nickname"})
    private String owner;

    @ApiModelProperty(example = "Example", value = "Title of the post", required = true)
    @NotBlank(message = "Title required")
    private String title;

    @ApiModelProperty(example = "text", value = "Text of the post", required = true)
    @NotBlank(message = "Text required")
    private String text;

    public PostDto(String owner, String title, String text) {
        this.owner = owner;
        this.title = title;
        this.text = text;
    }

}
