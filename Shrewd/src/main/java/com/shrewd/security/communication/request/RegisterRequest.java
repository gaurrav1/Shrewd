package com.shrewd.security.communication.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 10, max = 10)
    private String phone;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Setter
    @Getter
    private Set<String> role;


}