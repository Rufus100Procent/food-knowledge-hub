package com.example.foodknowledgehub.security.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterUserDto {

    @NotBlank
    @Size(min = 1, max = 50)
    private String fullName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String userName;

    @NotBlank(message = "password must be at least 8 charactor")
    @Size(min = 8, max = 100)
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
