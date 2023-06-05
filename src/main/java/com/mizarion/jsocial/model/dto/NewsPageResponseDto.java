package com.mizarion.jsocial.model.dto;

import com.mizarion.jsocial.model.dto.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsPageResponseDto {

    List<PostDto> posts;

    private int pageNum;

    private int pageSize;

    private int actualSize;

    public NewsPageResponseDto(List<PostDto> posts, int pageNum, int pageSize) {
        this.posts = posts;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.actualSize = posts.size();
    }
}
