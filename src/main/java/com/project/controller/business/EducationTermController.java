package com.project.controller.business;

import com.project.payload.request.business.EducationTermRequest;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.business.EducationTermResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;

    @PostMapping("/save/{educationTerm}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<EducationTermResponse>> saveEducationTerm(@RequestBody @Valid @PathVariable String educationTerm,
                                                                                    EducationTermRequest educationTermRequest) {
        return ResponseEntity.ok(educationTermService.saveEducationTerm(educationTermRequest));
    }

    @GetMapping("/{id}") //http://localhost:8080/educationTerms/1 + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public EducationTermResponse getEducationTermById(@PathVariable Long id){
        return educationTermService.getEducationTermById(id);
    }
}
