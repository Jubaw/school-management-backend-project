package com.project.controller.user;


import com.project.entity.concretes.user.User;
import com.project.entity.concretes.user.UserRole;
import com.project.payload.request.authentication.LoginRequest;
import com.project.payload.response.authentication.AuthResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login") //http://localhost:8080/auth/login + POST + JSON
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        return authenticationService.authenticateUser(loginRequest);
    }


    //TODO: HomeWork: AuthenticateUser service layer

    //TODO: HomeWokr: updatePassword(); --> Controller and service







}
