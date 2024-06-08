package com.project.payload.messages;

public class ErrorMessages {



    private ErrorMessages() {
    }

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";
    public static final String PASSWORD_NOT_MATCHED = "Your passwords are not matched" ;
    public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "User with username %s already registered" ;
    public static final String ALREADY_REGISTER_MESSAGE_SSN = "User with ssn %s already registered" ;
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "User with email %s already registered" ;
    public static final String ALREADY_REGISTER_MESSAGE_PHONE = "User with phone number %s already registered" ;

    public static final String ROLE_NOT_FOUND = "There is no role like that , check the database" ;

    public static final String NOT_FOUND_USER_MESSAGE =  "User not found with id %s";
    public static final String NOT_FOUND_ADVISOR_MESSAGE = "Advisor teacher with id %s not found";

    public static final String NOT_FOUND_USER_WITH_ROLE_MESSAGE = "The role information of the user with id %s is not role: %s" ;

    public static final String ALREADY_EXIST_ADVISOR_MESSAGE = "Advisor Teacher with id %s is already exist";



    public static final String EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE = "Error: The start date cannot be earlier than the last registration date " ;
    public static final String EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE = "Error: The end date cannot be earlier than the start date " ;
    public static final String EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE = "Error: Education Term with Term And Year already exist " ;
    public static final String EDUCATION_TERM_NOT_FOUND_MESSAGE = "Error: Education Term with id %s not found" ;
    public static final String EDUCATION_TERM_CONFLICT_MESSAGE = "Error: There is a conflict regarding the dates of the education terms.";

    public static final String ALREADY_EXISTS_WITH_LESSON_NAME = "Lesson with Lesson name %s already exists";
    public static final String NOT_FOUND_LESSON_MESSAGE = "Error: Lesson with this field %s not found";
    public static final String NOT_FOUND_LESSON_IN_LIST = "Error: Lesson not found in the list";

    public static final String TIME_NOT_VALID_MESSAGE = "Error: incorrect time";

    public static final String NOT_FOUND_LESSON_PROGRAM_MESSAGE = "Error: Lesson program with this field %s not found";

    public static final String NOT_FOUND_MEET = "Error: Meet with id %s not found";
    public static final String MEET_HOURS_CONFLICT = "meet hours has conflict with existing meets";


    public static final String STUDENT_INFO_NOT_FOUND = "Error: Student Info with id %d not found" ;







}

