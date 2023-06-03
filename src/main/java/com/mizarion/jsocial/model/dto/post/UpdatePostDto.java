package com.mizarion.jsocial.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto {

    @NotNull(message = "ID required")
    private Long id;
    @NotBlank(message = "Title required")
    private String title;
    @NotBlank(message = "Text required")
    private String text;

}
