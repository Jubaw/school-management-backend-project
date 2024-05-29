package com.project.controller.user;


import com.project.payload.request.User.UserRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save/{userRole}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(@RequestBody @Valid UserRequest userRequest,
                                                                  @PathVariable String userRole) {
        return ResponseEntity.ok(userService.saveUser(userRequest, userRole));
    }
// Not: getAllAdminOrDeanOrViceDeanByPage() ******************************************



    // Not :  getUserById() *********************************************************
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse>  getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }




// Not : deleteUser() **********************************************************
    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAnyAuthority('ADMIN')") // http://localhost:8080/user/deleteUser?userId=1 + DELETE
    public ResponseMessage<String> deleteUser(@RequestParam(value = "userId") Long id){
        return userService.deleteUser(id);
    }
    // Not: updateAdminOrDeanOrViceDean() ********************************************


    // Not: updateUserForUser() **********************************************************

    // Not : getByName() ***************************************************************
    @GetMapping("/{name}")
    public ResponseMessage<String> getByName(@PathVariable String name){
        return userService.getByName(name);
    }

}
