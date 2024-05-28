package com.project.service.user;

import com.project.entity.concretes.user.User;
import com.project.entity.concretes.user.UserRole;
import com.project.entity.enums.RoleType;
import com.project.exception.ResourceNotFound;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.User.UserRequest;
import com.project.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRequest userRequest;


    public UserRole getUserRole(RoleType roleType){
      return   userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(()->
              new ResourceNotFound(ErrorMessages.ROLE_NOT_FOUND));

    }

}