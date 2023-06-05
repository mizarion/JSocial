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
public class DeletePostDto {

    @ApiModelProperty(example = "1", value = "ID of the post")
    @NotBlank(message = "ID required")
    private Long id;

    @ApiModelProperty(example = "user1", value = "Owner of the post", required = true)
    @NotBlank(message = "Owner required")
    @JsonAlias(value={"name", "nickname"})
    private String owner;
}
