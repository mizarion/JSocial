package com.mizarion.jsocial.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequestDto {

    private String username;

    private int pageNum;

    private int pageSize;

}
