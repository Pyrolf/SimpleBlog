package com.gkwc.simpleblog.dto;

import javax.validation.constraints.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String username;
    @NotNull
    @Size(min = 8)
    private String password;
}
