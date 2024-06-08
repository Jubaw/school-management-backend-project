package com.project.service.business;

import com.project.entity.concretes.business.Meet;
import com.project.entity.concretes.user.User;
import com.project.entity.enums.RoleType;
import com.project.exception.BadRequestException;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.MeetMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.MeetRequest;
import com.project.payload.response.business.MeetResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.MeetRepository;
import com.project.service.helper.MethodHelper;
import com.project.service.helper.PageableHelper;
import com.project.service.user.UserService;
import com.project.service.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final MeetMapper meetMapper;
    private final MethodHelper methodHelper;
    private final DateTimeValidator dateTimeValidator;
    private final UserService userService;
    private final PageableHelper pageableHelper;


    public List<MeetResponse> getAll() {
        return meetRepository
                .findAll()
                .stream()
                .map(meetMapper::mapMeetToMeetResponse).collect(Collectors.toList());
    }

    public ResponseMessage<MeetResponse> getMeetById(Long meetId) {

        return ResponseMessage.<MeetResponse>builder()
                .message(SuccessMessages.MEET_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(meetMapper.mapMeetToMeetResponse(isMeetExistById(meetId)))
                .build();
    }


    private Meet isMeetExistById(Long meetId) {
        return meetRepository.
                findById(meetId).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_MEET, meetId)));

    }

    public ResponseMessage<MeetResponse> saveMeet(HttpServletRequest httpServletRequest
            , MeetRequest meetRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        User advisoryTeacher = methodHelper.isUserExistByUsername(username);
        //İsteği ileten teacher advisor mu kontrolü
        methodHelper.checkAdvisor(advisoryTeacher);

        //!!!Yeni meet saatlerinde çakışma var mı kontrolü
        dateTimeValidator.checkTimeWithException(meetRequest.getStartTime(), meetRequest.getStopTime());
        //!!! Teacherın eski meetleri ile çakışma var mı
        checkMeetConflict(advisoryTeacher.getId(),
                meetRequest.getDate(),
                meetRequest.getStartTime(),
                meetRequest.getStopTime());

        // !! Student için meet saatlerinde çakışma var mı kontrolü

        for (Long studentId : meetRequest.getStudentIds()) {
            User student = methodHelper.isUserExist(studentId);
            //Student mı kontrolü
            methodHelper.checkRole(student, RoleType.STUDENT);


            // Ogrencinin daha önceki saatleri ile çakışma var mı kontrolü
            checkMeetConflict(studentId, meetRequest.getDate(),
                    meetRequest.getStartTime(),
                    meetRequest.getStopTime());
        }

        // !!! Meete katılacak ogrenciler getiriliyor.
        List<User> students = userService.getStudentById(meetRequest.getStudentIds());
        // !!! DTO --> POJO
        Meet meet = meetMapper.mapMeetRequestToMeet(meetRequest);
        meet.setStudentList(students);
        meet.setAdvisorTeacher(advisoryTeacher);
        Meet savedMeet = meetRepository.save(meet);

        // !!! Ogrencilerin mail adreslerine mail gönderiliyor.
        // Send mail to service (savedMeet, "created a new meeting", students);

        return ResponseMessage.<MeetResponse>builder()
                .message(SuccessMessages.MEET_SAVE)
                .object(meetMapper.mapMeetToMeetResponse(savedMeet))
                .httpStatus(HttpStatus.OK)
                .build();
    }


    // bu metod hem teacher hemde student için çalışacak
    private void checkMeetConflict(Long userId, LocalDate date, LocalTime startTime, LocalTime stopTime) {
        List<Meet> meets;

        // !!! Student veya Teacher'a ait olan mevcut Meetler getiriliyor.

        if (Boolean.TRUE.equals(methodHelper.isUserExist(userId).getIsAdvisor())) {
            meets = meetRepository.getByAdvisorTeacher_IdEquals(userId); // Derived
        } else meets = meetRepository.findByStudentList_IdEquals(userId); //Derived

        // !! Çakışma kontrolü
        for (Meet meet : meets) {
            LocalTime existingStartTime = meet.getStartTime();
            LocalTime existingStopTime = meet.getStopTime();

            if (meet.getDate().equals(date) &&
                    (
                            (startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) || // if the new meeting starts after the existing meeting starts but before it ends.
                                    (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime)) || // if the new meeting ends after the existing meeting starts but before it ends.
                                    (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) || //if the new meeting covers the existing meeting
                                    (startTime.equals(existingStartTime) || stopTime.equals(existingStopTime)) //if the new meeting starts or ends exactly when the existing meeting starts or ends.
                    )
            ) {
                throw new ConflictException(ErrorMessages.MEET_HOURS_CONFLICT);
            }


        }

    }

    public ResponseMessage<MeetResponse> updateMeet(MeetRequest updateMeetRequest, Long meetId, HttpServletRequest httpServletRequest) {
        Meet meet = isMeetExistById(meetId);
        // Teacher ise sadece kendi Meetlerini güncelleyebilsin.
        isTeacherControl(meet, httpServletRequest);
        dateTimeValidator.checkTimeWithException(updateMeetRequest.getStartTime(), updateMeetRequest.getStopTime());

        if (!( // update de unique olmasi gereken bilgiler eskisi ile ayni ie conflict kontrolune gerek yok :
                meet.getDate().equals(updateMeetRequest.getDate()) &&
                        meet.getStartTime().equals(updateMeetRequest.getStartTime()) &&
                        meet.getStopTime().equals(updateMeetRequest.getStopTime())
        )
        ) {
            // !!! Student ıcın çakısma var mı kontrolu
            for (Long studentId : updateMeetRequest.getStudentIds()) {
                checkMeetConflict(studentId,
                        updateMeetRequest.getDate()
                        , updateMeetRequest.getStartTime()
                        , updateMeetRequest.getStopTime());
            }
            // !!! teacher icin cakisma var mi kontrolu
            checkMeetConflict(meet.getAdvisorTeacher().getId(),
                    updateMeetRequest.getDate()
                    , updateMeetRequest.getStartTime()
                    , updateMeetRequest.getStopTime());
        }

        //Studentlar getiriliyor
        List<User> students = userService.getStudentById(updateMeetRequest.getStudentIds());
        // !!! DTO --> POJO
        Meet updateMeet = meetMapper.mapMeetUpdateRequestToMeet(updateMeetRequest, meetId);
        // !!! Meet objesine studentlar setleniyor.
        updateMeet.setStudentList(students);
        updateMeet.setAdvisorTeacher(meet.getAdvisorTeacher());
        Meet updatedMeet = meetRepository.save(updateMeet);
        //sendMailToService(updateMeet, "updated meet",students);
        return ResponseMessage.<MeetResponse>builder()
                .message(SuccessMessages.MEET_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(meetMapper.mapMeetToMeetResponse(updatedMeet))
                .build();
    }


    private void isTeacherControl(Meet meet, HttpServletRequest httpServletRequest) {
        // Teacher ise sadece kendi meetlerini setleyebilsin.
        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.isUserExistByUsername(username);
//        methodHelper.checkAdvisor(teacher);
        if ((teacher.getUserRole().getRoleType().equals(RoleType.TEACHER)) &&
                !(meet.getAdvisorTeacher().getId().equals(teacher.getId()))) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

    }

    public ResponseMessage delete(Long meetId, HttpServletRequest httpServletRequest) {
        Meet meet = isMeetExistById(meetId);
        isTeacherControl(meet, httpServletRequest);
        meetRepository.deleteById(meetId);

        return ResponseMessage.builder()
                .message(SuccessMessages.MEET_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseEntity<List<MeetResponse>> getAllByTeacher(HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        User advisorTeacher = methodHelper.isUserExistByUsername(username);
        methodHelper.checkAdvisor(advisorTeacher);

        List<MeetResponse> meetResponseList = meetRepository.getByAdvisorTeacher_IdEquals(advisorTeacher.getId())
                .stream()
                .map(meetMapper::mapMeetToMeetResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(meetResponseList);
    }

    public ResponseEntity<List<MeetResponse>> getAllMeetByStudent(HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        User student = methodHelper.isUserExistByUsername(username);
//        methodHelper.checkRole(student,RoleType.STUDENT);

        List<MeetResponse> meetResponseList = meetRepository.findByStudentList_IdEquals(student.getId())
                .stream()
                .map(meetMapper::mapMeetToMeetResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(meetResponseList);
    }

    public Page<MeetResponse> getAllMeetByPage(int page, int size) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);
        return meetRepository.findAll(pageable).map(meetMapper::mapMeetToMeetResponse);
    }

    public ResponseEntity<Page<MeetResponse>> getAllMeetByTeacher(HttpServletRequest httpServletRequest, int size, int page) {
        String username = (String) httpServletRequest.getAttribute("username");
        User advisorTeacher = methodHelper.isUserExistByUsername(username);
        methodHelper.checkAdvisor(advisorTeacher);

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        return ResponseEntity.ok(meetRepository
                .findByAdvisorTeacher_IdEquals(advisorTeacher.getId(),pageable)
                .map(meetMapper::mapMeetToMeetResponse));
    }
}
