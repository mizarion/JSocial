package com.mizarion.jsocial.model.dto.post;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Data
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationTime;

    public PostDto(String owner, String title, String text) {
        this.owner = owner;
        this.title = title;
        this.text = text;
    }

    public PostDto(String owner, String title, String text, LocalDateTime creationTime) {
        this.owner = owner;
        this.title = title;
        this.text = text;
        this.creationTime = creationTime.truncatedTo(ChronoUnit.MILLIS);
    }

    public PostDto(Long id, String owner, String title, String text, LocalDateTime creationTime) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.text = text;
        this.creationTime = creationTime.truncatedTo(ChronoUnit.MILLIS);
    }
}
