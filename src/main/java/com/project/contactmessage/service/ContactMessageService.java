package com.project.contactmessage.service;


import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMesssage;
import com.project.contactmessage.mapper.ContactMessageMapper;
import com.project.contactmessage.repository.ContactMessageRepository;
import com.project.exception.ResourceNotFound;
import com.project.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactMessageService {


    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper createContactMessage;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {
        ContactMesssage contactMessage = createContactMessage.requestToContactMessage(contactMessageRequest);
        ContactMesssage savedContactMessage = contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(createContactMessage.contactMessageToResponse(savedContactMessage))
                .build();
    }


    public ResponseMessage<Page<ContactMessageResponse>> getAllByPage(Pageable pageable) {
        Page<ContactMesssage> contactMessages = contactMessageRepository.findAll(pageable);
        Page<ContactMessageResponse> responsePage = contactMessages.map(createContactMessage::contactMessageToResponse);
        return ResponseMessage.<Page<ContactMessageResponse>>builder()
                .message("All contact Messages retrieved")
                .httpStatus(HttpStatus.OK)
                .object(responsePage)
                .build();
    }

    public ResponseMessage<Page<ContactMessageResponse>> searchEmailByPage(String email, Pageable pageable) {
        Page<ContactMesssage> contactMessages = contactMessageRepository.findByEmailContaining(email, pageable);
        Page<ContactMessageResponse> responsePage = contactMessages.map(createContactMessage::contactMessageToResponse);
        return ResponseMessage.<Page<ContactMessageResponse>>builder()
                .message("Contact Messages retrieved by Email")
                .httpStatus(HttpStatus.OK)
                .object(responsePage)
                .build();
    }

    public ResponseMessage<Page<ContactMessageResponse>> searchBySubject(String subject, Pageable pageable) {
        Page<ContactMesssage> contactMesssages = contactMessageRepository.searchBySubject(subject, pageable);
        Page<ContactMessageResponse> responsePage = contactMesssages.map(createContactMessage::contactMessageToResponse);
        return ResponseMessage.<Page<ContactMessageResponse>>builder()
                .message("Contact Messages retrieved by Subject")
                .httpStatus(HttpStatus.OK)
                .object(responsePage)
                .build();
    }

    public ResponseMessage<Void> deleteByIdParam(Long id) {
        Optional<ContactMesssage> contactMesssageOptional = contactMessageRepository.findById(id);
        if (contactMesssageOptional.isPresent()) {
            contactMessageRepository.deleteById(id);
            return ResponseMessage.<Void>builder()
                    .message("Contact Messages retrieved by Subject")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<Void>builder()
                    .message("Contact Message Not Found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }


    public ResponseMessage<ContactMessageResponse> getContactMessageByIdParam(Long id) {
        ContactMesssage contactMesssage = contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource not found"));
       ContactMessageResponse response = createContactMessage.contactMessageToResponse(contactMesssage);
        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Messages retrieved by Id")
                .httpStatus(HttpStatus.FOUND)
                .object(response)
                .build();
    }


    public ResponseMessage<ContactMessageResponse> getContactMessageByPathVar(Long id) {
        ContactMesssage contactMesssage = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource not found"));
        ContactMessageResponse response = createContactMessage.contactMessageToResponse(contactMesssage);
        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message retrieved by Id")
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }
}


//    public Page<ContactMessageResponse> getAllByPage(Pageable pageable,ContactMessageRequest contactMessageRequest) {
//        ContactMesssage contactMesssage = createContactMessage.requestToContactMessage(contactMessageRequest);
//
//    }


// Not: ******************************************** getAllByPage *************************************** -- DONE

// Not: ************************************* searchByEmailByPage *************************************** -- done

// Not: *************************************** searchBySubjectByPage ***************************************- Done

// Not: searchByDateBetween ***************************************

// Not: searchByTimeBetween ***************************************

// Not: *********************************** deleteByIdParam *************************************** - Done

// Not: ***************************************** deleteById ***************************************-Done

// Not: *********************************** getByIdWithParam ***************************************-Done

// Not: ************************************ getByIdWithPath ***************************************-Done

