package com.project.service.validator;

import com.project.exception.ConflictException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.project.entity.concretes.user.User;
import com.project.payload.request.abstracts.AbstractUserRequest;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private UserRepository userRepository;

    public void checkDuplicate(String username, String ssn, String phone, String email) {


        if (userRepository.existsByUsername(username)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        }

        if (userRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        }

        if (userRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE, phone));
        }

        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }

    }

    public void checkUniqueProperties(User user, AbstractUserRequest abstractUserRequest) {
        String updatedUsername = "";
        String updatedSnn = "";
        String updatedPhone = "";
        String updatedEmail = "";
        boolean isChanged = false;
        if (!user.getUsername().equalsIgnoreCase(abstractUserRequest.getUsername())) {
            updatedUsername = abstractUserRequest.getUsername();
            isChanged = true;
        }
        if (!user.getSsn().equalsIgnoreCase(abstractUserRequest.getSsn())) {
            updatedSnn = abstractUserRequest.getSsn();
            isChanged = true;
        }
        if (!user.getPhoneNumber().equalsIgnoreCase(abstractUserRequest.getPhoneNumber())) {
            updatedPhone = abstractUserRequest.getPhoneNumber();
            isChanged = true;
        }
        if (!user.getEmail().equalsIgnoreCase(abstractUserRequest.getEmail())) {
            updatedEmail = abstractUserRequest.getEmail();
            isChanged = true;
        }

        if (isChanged) {
            checkDuplicate(updatedUsername, updatedSnn, updatedPhone, updatedEmail);
        }

    }
}

