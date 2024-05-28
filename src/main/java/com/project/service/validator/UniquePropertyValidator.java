package com.project.service.validator;

import com.project.exception.ConflictException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {


    private  UserRepository userRepository;

    public void checkForDuplicate(String username,String ssn, String phone,String email){
        if (userRepository.existsByUsername(username)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME));
        }
        if (userRepository.existsBySssn(ssn)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_EXISTS_BY_SSN));
        }
        if (userRepository.existsByPhoneNumber(phone)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_EXISTS_BY_PHONE));
        }
        if (userRepository.existsByEmail(email)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_EXISTS_BY_EMAIL));
        }


    }





}
