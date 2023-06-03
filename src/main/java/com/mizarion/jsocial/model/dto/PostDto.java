package com.mizarion.jsocial.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private String owner;

    private String title;

    private String text;

    // todo: creation time
}
