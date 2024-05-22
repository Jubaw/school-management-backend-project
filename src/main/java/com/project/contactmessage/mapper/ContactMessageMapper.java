package com.project.contactmessage.mapper;


import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMesssage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ContactMessageMapper {

    //POJO --> DTO
    public ContactMessageResponse contactMessageToResponse(ContactMesssage contactMesssage){

        return ContactMessageResponse.builder()
                .name(contactMesssage.getName()).subject(contactMesssage.getSubject())

                .subject(contactMesssage.getEmail())
                .message(contactMesssage.getMessage())
                .email(contactMesssage.getEmail())
                .dateTime(LocalDateTime.now())
                .build();
    }

    public ContactMesssage requestToContactMessage(ContactMessageRequest contactMessageRequest){
        return ContactMesssage.builder()
                .name(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .email(contactMessageRequest.getEmail())
                .dateTime(LocalDateTime.now())
                .build();

    }



    //DTO --> POJO

}
