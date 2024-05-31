package com.project.controller.user;

import com.project.payload.request.user.StudentRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;



    @PostMapping("/save") // http:/localhost:8080/student/save
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<StudentResponse>> saveStudent(@RequestBody @Valid StudentRequest request){

        return ResponseEntity.ok(studentService.saveStudent(request));
    }

    // Not: updateStudentForStudents() ***********************************************
    // !!! ogrencinin kendisini update etme islemi


    // Not: updateStudent() **********************************************************

    // Not: ChangeActıveStatusOfStudent() ********************************************



}
