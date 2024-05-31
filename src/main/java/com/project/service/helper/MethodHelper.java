package com.project.service.helper;

import com.project.entity.concretes.user.User;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFound;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {
    private final UserRepository userRepository;

    // !!! isUserExist
    public User isUserExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFound(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,
                        userId)));
    }

    // !!! checkBuiltIn
    public void checkBuiltIn(User user){
        if(Boolean.TRUE.equals(user.getBuilt_in())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }
    //!!! isUserExistsWithUsername
    public User isUserExistsByUsername(String username){
        User user =  userRepository.findByUsername(username);
        if (user.getId() == null){
            throw new ResourceNotFound(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }
        return user;
    }

    //!!! Advisor kontrolü
    public void checkAdvisor(User user){
        if (Boolean.FALSE.equals(user.getIsAdvisor())){
            throw new ResourceNotFound(String.format(
                    ErrorMessages.NOT_FOUND_ADVISOR_MESSAGE,
                    user.getId()));
        }
    }
}