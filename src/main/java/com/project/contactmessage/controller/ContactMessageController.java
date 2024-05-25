package com.project.contactmessage.controller;


import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.service.ContactMessageService;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/save") // http://localhost:8080/contactMessages/save + POST + JSON
    public ResponseMessage<ContactMessageResponse> save(@Valid @RequestBody ContactMessageRequest contactMessageRequest){
        return  contactMessageService.save(contactMessageRequest);
    }

    @GetMapping("/getAll") // http://localhost:8080/contactMessages/getAll + GET
    public Page<ContactMessageResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return contactMessageService.getAll(page,size,sort,type);
    }

    @GetMapping("/searchByEmail")  // http://localhost:8080/contactMessages/searchByEmail?email=aaa@bbb.com  + GET
    public Page<ContactMessageResponse> searchByEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.searchByEmail(email,page,size,sort,type);
    }

    // Not: Odev : searchBySubject *******************************************
    @GetMapping("/searchBySubject")// http://localhost:8080/contactMessages/searchBySubject?subject=deneme
    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type){
        return contactMessageService.searchBySubject(subject,page,size,sort,type);
    }

    // ODEV : No Content Type 204 kodu
    @DeleteMapping("/deleteById/{contactMessageId}") // http://localhost:8080/contactMessages/deleteById/2 + DELETE
    public ResponseEntity<String> deleteByIdPath(@PathVariable Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }

    // Not: Odev2:deleteByIdParam ********************************************
    @DeleteMapping("/deleteByIdParam")  //http://localhost:8080/contactMessages/deleteByIdParam?contactMessageId=2
    public ResponseEntity<String> deleteById(@RequestParam(value = "contactMessageId") Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId)); // servisdeki ayni metod

        //TODO: RETURN NO CONTENT TYPE (204)
    }

    @GetMapping("/searchBetweenDates")  //http://localhost:8080/contactMessages/searchBetweenDates?beginDate=2023-09-13&endDate=2023-09-15   + GET
    public ResponseEntity<List<ContactMessage>> searchBetweenDates(
            @RequestParam(value = "beginDate") String beginDateString,
            @RequestParam(value = "endDate") String endDateString
    ) {
        List<ContactMessage> contactMessages = contactMessageService.searchBetweenDates(beginDateString, endDateString);
        return ResponseEntity.ok(contactMessages);
    }



    @GetMapping("/searchBetweenTimes")//http://localhost:8080/contactMessages/searchBetweenTimes?startHour=09&startMinute=00&endHour=17&endMinute=30  + GET
    public ResponseEntity<List<ContactMessage>> searchBetweenTimes(
            @RequestParam(value = "startHour") String startHourString,
            @RequestParam(value = "startMinute") String startMinuteString,
            @RequestParam(value = "endHour") String endHourString,
            @RequestParam(value = "endMinute") String endMinuteString

    ){
        List<ContactMessage> contactMessages = contactMessageService.searchBetweenTimes(startHourString,startMinuteString,endHourString,endMinuteString);
        return ResponseEntity.ok(contactMessages);
    }

    //    @GetMapping("/searchByTimeBetween")
//    public ResponseMessage<Page<ContactMessageResponse>> searchByTimeBetween(
//            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalTime startTime,
//            @RequestParam("endTime")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalTime endTime,
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @RequestParam(value = "sort", defaultValue = "id") String prop,
//            @RequestParam("direction") Sort.Direction direction){

    @GetMapping("/getByIdParam")//http://localhost:8080/contactMessages/getByIdParam?contactMessageId=1  + GET
    public ResponseEntity<ContactMessage> getById(@RequestParam(value = "contactMessageId") Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }

    @GetMapping("/getById/{contactMessageId}")//http://localhost:8080/contactMessages/getById/1  + GET
    public ResponseEntity<ContactMessage> getByIdPath(@PathVariable Long contactMessageId) {
        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }




























//    private final ContactMessageService contactMessageService;
//
//
//    @PostMapping("/save")
//    public ResponseMessage<ContactMessageResponse> saveContact(@Valid @RequestParam ContactMessageRequest contactMessageRequest) {
//        return contactMessageService.save(contactMessageRequest);
//    }
//
//
//    @GetMapping
//    public Page<ContactMessageResponse> getAll(
//            @RequestParam(value = "page",defaultValue = "0") int page,
//            @RequestParam(value = "size",defaultValue = "10") int size,
//            @RequestParam(value = "sort",defaultValue = "dateTime") String sort,
//            @RequestParam(value = "type",defaultValue = "desc") String type
//    ){
//        return contactMessageService.getAll(page,size,sort,type);
//    }
//    @GetMapping("/getAll")
//    public ResponseMessage<Page<ContactMessageResponse>> getAllByPage(@RequestParam("page") int page,
//                                                                      @RequestParam("size") int size,
//                                                                      @RequestParam(value = "sort", defaultValue = "id") String prop,
//                                                                      @RequestParam("direction") Sort.Direction direction) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
//        return contactMessageService.getAllByPage(pageable);
//    }

//    @GetMapping("/searchByEmail")
//    public ResponseMessage<Page<ContactMessageResponse>> searchEmailByPage(@RequestParam("email") String email,
//                                                                           @RequestParam("page") int page,
//                                                                           @RequestParam("size") int size,
//                                                                           @RequestParam(value = "sort", defaultValue = "id") String prop,
//                                                                           @RequestParam("direction") Sort.Direction direction) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
//        return contactMessageService.searchEmailByPage(email, pageable);
//    }
//
//    @GetMapping("/searchBySubject")
//    public ResponseMessage<Page<ContactMessageResponse>> searchBySubject(@RequestParam("subject") String subject,
//                                                                         @RequestParam("page") int page,
//                                                                         @RequestParam("size") int size,
//                                                                         @RequestParam(value = "sort", defaultValue = "id") String prop,
//                                                                         @RequestParam("direction") Sort.Direction direction) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
//        return contactMessageService.searchBySubject(subject, pageable);
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseMessage<Void> deleteByIdParam(@Valid @RequestParam("id") Long id
//    ) {
//        return contactMessageService.deleteByIdParam(id);
//    }
//
//    @GetMapping("/page")
//    public ResponseMessage<ContactMessageResponse> getContactMessageByIdParam(@RequestParam("id") Long id){
//        return contactMessageService.getContactMessageByIdParam(id);
//    }
//
//    @GetMapping("/page/{id}")
//    public ResponseMessage<ContactMessageResponse> getContactMessageByPathVar(@PathVariable("id") Long id) {
//        return contactMessageService.getContactMessageByPathVar(id);
//    }
//
//    @GetMapping("/searchByDateBetween")
//    public ResponseMessage<Page<ContactMessageResponse>> searchByDateBetween(
//                                                                             @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
//                                                                             @RequestParam("endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate,
//                                                                             @RequestParam("page") int page,
//                                                                             @RequestParam("size") int size,
//                                                                             @RequestParam(value = "sort", defaultValue = "id") String prop,
//                                                                             @RequestParam("direction") Sort.Direction direction){
//
//
//
//        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
//        return contactMessageService.searchByDateBetween(startDate,endDate,pageable);
//
//    }
//

//
//
//
//        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
//        return contactMessageService.searchByTimeBetween(startTime,endTime,pageable);
//
//    }

}
