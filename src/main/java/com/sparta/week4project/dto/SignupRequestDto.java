package com.sparta.week4project.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Size(min=4,max=10)
    @Pattern(regexp = "[a-z0-9]+")
    private String username;
    @Pattern(regexp = "[a-zA-Z0-9]+") //
    @Size(min=8,max=15)
    private String password;
    private boolean admin;
    private String adminToken;
}
