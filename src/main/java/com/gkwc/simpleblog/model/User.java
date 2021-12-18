package com.gkwc.simpleblog.model;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String email;
}
