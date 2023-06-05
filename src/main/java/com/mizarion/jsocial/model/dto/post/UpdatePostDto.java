package com.mizarion.jsocial.model.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto {

    @ApiModelProperty(example = "1", value = "ID of the post", required = true)
    @NotNull(message = "ID required")
    private Long id;

    @ApiModelProperty(example = "Example", value = "Title of the post", required = true)
    @NotBlank(message = "Title required")
    private String title;

    @ApiModelProperty(example = "text", value = "Text of the post", required = true)
    @NotBlank(message = "Text required")
    private String text;

}
