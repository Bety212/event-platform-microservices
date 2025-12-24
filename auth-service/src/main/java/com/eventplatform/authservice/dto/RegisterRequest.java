package com.eventplatform.authservice.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;

    private String firstName;
    private String lastName;

    private String email;
    private String confirmEmail;

    private String phone;

    private String password;
    private String confirmPassword;
    private String role;   // ⭐ AJOUT IMPORTANT

}
