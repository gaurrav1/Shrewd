package com.shrewd.security.communication.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrgRegisterRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String org_name;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    private String phone;

    @NotBlank
    @Size(min = 3, max = 200)
    private String address;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 3, max = 40)
    private String tenant;

}