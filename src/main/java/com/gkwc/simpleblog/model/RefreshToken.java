package com.gkwc.simpleblog.model;

import javax.persistence.*;
import java.time.Instant;

import lombok.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant createdOn;
}
