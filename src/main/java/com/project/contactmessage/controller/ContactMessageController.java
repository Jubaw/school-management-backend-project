package com.project.contactmessage.controller;


import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.service.ContactMessageService;
import com.project.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;


    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> saveContact(@Valid @RequestParam ContactMessageRequest contactMessageRequest){
        return contactMessageService.save(contactMessageRequest);
    }





}
