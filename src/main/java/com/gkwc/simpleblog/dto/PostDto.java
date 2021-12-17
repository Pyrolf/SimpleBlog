package com.gkwc.simpleblog.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String username;
}
