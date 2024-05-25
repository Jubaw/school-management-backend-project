package com.project.payload.request.authentication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "Username cannot be empty")
    private String userName;

    @NotNull(message = "Password cannot be empty")
    private String password;

}
