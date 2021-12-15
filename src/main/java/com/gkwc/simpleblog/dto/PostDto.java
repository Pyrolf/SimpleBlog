package com.gkwc.simpleblog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String username;
}
