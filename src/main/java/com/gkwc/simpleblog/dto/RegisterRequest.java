package com.gkwc.simpleblog.dto;

import javax.validation.constraints.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 8)
    private String password;
}
