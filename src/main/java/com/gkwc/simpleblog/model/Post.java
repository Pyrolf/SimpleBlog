package com.gkwc.simpleblog.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import lombok.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @Lob
    @NotEmpty
    private String content;
    private Instant createdOn;
    private Instant updatedOn;
    @NotBlank
    private String username;
}
