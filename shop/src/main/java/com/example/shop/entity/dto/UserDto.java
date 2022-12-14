package com.example.shop.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    @Size(min=2, max=30,message = "Name must be between 2 and 30 characters")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$")
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$")
    private String repeatPassword;
    @Email(message = "Email should be valid")
    private String email;


}
