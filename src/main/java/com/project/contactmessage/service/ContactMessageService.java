package com.project.contactmessage.service;


import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.mapper.ContactMessageMapper;
import com.project.contactmessage.messages.Messages;
import com.project.contactmessage.repository.ContactMessageRepository;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFound;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactMessageService {


    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

        ContactMessage contactMessage = contactMessageMapper.requestToContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully")
                .httpStatus(HttpStatus.CREATED) // 201
                .object(contactMessageMapper.contactMessageToResponse(savedData))
                .build();
    }


    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type, "desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findAll(pageable).map(contactMessageMapper::contactMessageToResponse);
    }

    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type, "desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findByEmailEquals(email, pageable).
                map(contactMessageMapper::contactMessageToResponse);
    }

    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findBySubjectEquals(subject, pageable). // Derived
                map(contactMessageMapper::contactMessageToResponse);
    }


    public String deleteById(Long id) {
        getContactMessageById(id);
        contactMessageRepository.deleteById(id);
        return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;
    }

    public ContactMessage getContactMessageById(Long id){
        return contactMessageRepository.findById(id).orElseThrow(()->
                new ResourceNotFound(Messages.NOT_FOUND_MESSAGE));
    }



    public List<ContactMessage> searchBetweenDates(String beginDateString, String endDateString) {

        try {
            LocalDate beginDate = LocalDate.parse(beginDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            return contactMessageRepository.findMessagesBetweenDates(beginDate, endDate);
        } catch (DateTimeParseException e) {
            throw new ConflictException(Messages.WRONG_DATE_MESSAGE);
        }
    }


    public List<ContactMessage> searchBetweenTimes(String startHourString, String startMinuteString, String endHourString, String endMinuteString) {

        try {
            int startHour = Integer.parseInt(startHourString);
            int startMinute = Integer.parseInt(startMinuteString);
            int endHour = Integer.parseInt(endHourString);
            int endMinute = Integer.parseInt(endMinuteString);

            return contactMessageRepository.findMessagesBetweenTimes(startHour,startMinute,endHour,endMinute);
        } catch (NumberFormatException e) {
            throw new ConflictException(Messages.WRONG_TIME_MESSAGE);
        }
    }
























//
//    private final ContactMessageRepository contactMessageRepository;
//    private final ContactMessageMapper contactMessageMapper;
//
//    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {
//        ContactMessage contactMessage = contactMessageMapper.requestToContactMessage(contactMessageRequest);
//        ContactMessage savedContactMessage = contactMessageRepository.save(contactMessage);
//        return ResponseMessage.<ContactMessageResponse>builder()
//                .message("Contact Message Created Successfully")
//                .httpStatus(HttpStatus.CREATED)
//                .object(contactMessageMapper.contactMessageToResponse(savedContactMessage))
//                .build();
//    }
//
//    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {
//
//        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
//        if (Objects.equals(type,"desc")){
//            pageable = PageRequest.of(page,size,Sort.by(sort).descending());
//        }
//        return contactMessageRepository.findAll(pageable).map(contactMessageMapper::contactMessageToResponse);
//
//    }



//    public ResponseMessage<Page<ContactMessageResponse>> getAllByPage(Pageable pageable) {
//        Page<ContactMessage> contactMessages = contactMessageRepository.findAll(pageable);
//        Page<ContactMessageResponse> responsePage = contactMessages.map(createContactMessage::contactMessageToResponse);
//        return ResponseMessage.<Page<ContactMessageResponse>>builder()
//                .message("All contact Messages retrieved")
//                .httpStatus(HttpStatus.OK)
//                .object(responsePage)
//                .build();
//    }
//
//    public ResponseMessage<Page<ContactMessageResponse>> searchEmailByPage(String email, Pageable pageable) {
//        Page<ContactMessage> contactMessages = contactMessageRepository.findByEmailContaining(email, pageable);
//        Page<ContactMessageResponse> responsePage = contactMessages.map(contactMessageMapper::contactMessageToResponse);
//        return ResponseMessage.<Page<ContactMessageResponse>>builder()
//                .message("Contact Messages retrieved by Email")
//                .httpStatus(HttpStatus.OK)
//                .object(responsePage)
//                .build();
//    }
//
//    public ResponseMessage<Page<ContactMessageResponse>> searchBySubject(String subject, Pageable pageable) {
//        Page<ContactMessage> contactMesssages = contactMessageRepository.searchBySubject(subject, pageable);
//        Page<ContactMessageResponse> responsePage = contactMesssages.map(contactMessageMapper::contactMessageToResponse);
//        return ResponseMessage.<Page<ContactMessageResponse>>builder()
//                .message("Contact Messages retrieved by Subject")
//                .httpStatus(HttpStatus.OK)
//                .object(responsePage)
//                .build();
//    }
//
//    public ResponseMessage<Void> deleteByIdParam(Long id) {
//        Optional<ContactMessage> contactMesssageOptional = contactMessageRepository.findById(id);
//        if (contactMesssageOptional.isPresent()) {
//            contactMessageRepository.deleteById(id);
//            return ResponseMessage.<Void>builder()
//                    .message("Contact Messages retrieved by Subject")
//                    .httpStatus(HttpStatus.OK)
//                    .build();
//        } else {
//            return ResponseMessage.<Void>builder()
//                    .message("Contact Message Not Found")
//                    .httpStatus(HttpStatus.NOT_FOUND)
//                    .build();
//        }
//    }
//
//
//    public ResponseMessage<ContactMessageResponse> getContactMessageByIdParam(Long id) {
//        ContactMessage contactMesssage = contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource not found"));
//       ContactMessageResponse response = contactMessageMapper.contactMessageToResponse(contactMesssage);
//        return ResponseMessage.<ContactMessageResponse>builder()
//                .message("Contact Messages retrieved by Id")
//                .httpStatus(HttpStatus.FOUND)
//                .object(response)
//                .build();
//    }
//
//
//    public ResponseMessage<ContactMessageResponse> getContactMessageByPathVar(Long id) {
//        ContactMessage contactMesssage = contactMessageRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFound("Resource not found"));
//        ContactMessageResponse response = contactMessageMapper.contactMessageToResponse(contactMesssage);
//        return ResponseMessage.<ContactMessageResponse>builder()
//                .message("Contact Message retrieved by Id")
//                .httpStatus(HttpStatus.OK)
//                .object(response)
//                .build();
//    }
//
//    public ResponseMessage<Page<ContactMessageResponse>> searchByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable) {
//        Page<ContactMessage> contactMesssages = contactMessageRepository.findByDateBetween(startDate,endDate,pageable);
//        Page<ContactMessageResponse> responsePage = contactMesssages.map(contactMessageMapper::contactMessageToResponse);
//        return ResponseMessage.<Page<ContactMessageResponse>>builder()
//                .message("Contact Messages retrieved with between dates")
//                .httpStatus(HttpStatus.OK)
//                .object(responsePage)
//                .build();
//    }
//
//    public ResponseMessage<Page<ContactMessageResponse>> searchByTimeBetween(LocalTime startTime, LocalTime endTime, Pageable pageable) {
//        Page<ContactMessage> contactMesssages = contactMessageRepository.findByTimeBetween(startTime,endTime,pageable);
//        Page<ContactMessageResponse> responsePage = contactMesssages.map(contactMessageMapper::contactMessageToResponse);
//        return ResponseMessage.<Page<ContactMessageResponse>>builder()
//                .message("Contact Messages retrieved with between dates")
//                .httpStatus(HttpStatus.OK)
//                .object(responsePage)
//                .build();
//    }


}


//    public Page<ContactMessageResponse> getAllByPage(Pageable pageable,ContactMessageRequest contactMessageRequest) {
//        ContactMessage contactMesssage = createContactMessage.requestToContactMessage(contactMessageRequest);
//
//    }


// Not: ******************************************** getAllByPage *************************************** -- DONE

// Not: ************************************* searchByEmailByPage *************************************** -- done

// Not: *************************************** searchBySubjectByPage ***************************************- Done

// Not: searchByDateBetween ***************************************-Done

// Not: searchByTimeBetween ***************************************-Done

// Not: *********************************** deleteByIdParam *************************************** - Done

// Not: ***************************************** deleteById ***************************************-Done

// Not: *********************************** getByIdWithParam ***************************************-Done

// Not: ************************************ getByIdWithPath ***************************************-Done

