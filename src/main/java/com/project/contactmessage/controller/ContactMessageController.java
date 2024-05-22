package com.project.contactmessage.controller;


import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.service.ContactMessageService;
import com.project.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;


    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> saveContact(@Valid @RequestParam ContactMessageRequest contactMessageRequest) {
        return contactMessageService.save(contactMessageRequest);
    }


    @GetMapping("/page")
    public ResponseMessage<Page<ContactMessageResponse>> getAllByPage(@RequestParam("page") int page,
                                                                      @RequestParam("size") int size,
                                                                      @RequestParam(value = "sort", defaultValue = "id") String prop,
                                                                      @RequestParam("direction") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return contactMessageService.getAllByPage(pageable);
    }

    @GetMapping("/searchByEmail")
    public ResponseMessage<Page<ContactMessageResponse>> searchEmailByPage(@RequestParam("email") String email,
                                                                           @RequestParam("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam(value = "sort", defaultValue = "id") String prop,
                                                                           @RequestParam("direction") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return contactMessageService.searchEmailByPage(email, pageable);
    }

    @GetMapping("/searchBySubject")
    public ResponseMessage<Page<ContactMessageResponse>> searchBySubject(@RequestParam("subject") String subject,
                                                                         @RequestParam("page") int page,
                                                                         @RequestParam("size") int size,
                                                                         @RequestParam(value = "sort", defaultValue = "id") String prop,
                                                                         @RequestParam("direction") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return contactMessageService.searchBySubject(subject, pageable);
    }

    @DeleteMapping("/delete")
    public ResponseMessage<Void> deleteByIdParam(@Valid @RequestParam("id") Long id
    ) {
        return contactMessageService.deleteByIdParam(id);
    }

    @GetMapping("/page")
    public ResponseMessage<ContactMessageResponse> getContactMessageByIdParam(@RequestParam("id") Long id){
        return contactMessageService.getContactMessageByIdParam(id);
    }

    @GetMapping("/page/{id}")
    public ResponseMessage<ContactMessageResponse> getContactMessageByPathVar(@PathVariable("id") Long id) {
        return contactMessageService.getContactMessageByPathVar(id);
    }


}
