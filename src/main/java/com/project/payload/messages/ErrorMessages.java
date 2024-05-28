package com.project.payload.messages;

public class ErrorMessages {



    private ErrorMessages() {
    }

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";


    public static final String PASSWORD_NOT_MATCHED = "Your passwords are not matched" ;



    public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "Error: User with username %s already registered" ;
    public static final String ALREADY_EXISTS_BY_SSN = "Error: User with SSN %s already registered";
    public static final String ALREADY_EXISTS_BY_PHONE = "Error: User with phone %s already registered";
    public static final String ALREADY_EXISTS_BY_EMAIL = "Error: User with email %s already registered" ;


    public static final String ROLE_NOT_FOUND = "Error: User with role %s not found" ;







}

