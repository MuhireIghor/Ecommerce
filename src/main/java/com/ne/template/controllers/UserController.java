package com.ne.template.controllers;

import com.ne.template.dtos.requests.SignUpDto;
import com.ne.template.enums.ERole;
import com.ne.template.exceptions.ResourceNotFoundException;
import com.ne.template.models.User;
import com.ne.template.serviceImpls.RoleServiceImpl;
import com.ne.template.serviceImpls.UserServiceImpl;
import com.ne.template.services.IRoleService;
import com.ne.template.services.IUserService;
import com.ne.template.utils.ApiResponse;
import com.ne.template.utils.ExceptionUtils;
import com.ne.template.utils.HashUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;
    private final IRoleService roleService;

    @Autowired
    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }


    @GetMapping(path = "/allUser")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Users fetched successfully",
                    userService.getAllUsers()));
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable(value = "id") UUID userId) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "User fetched Successfully", userService.getUserById(userId)));

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<ApiResponse> getUserByEmail(@PathVariable(value = "email") String email) {
        try {
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "User found successfully",
                    userService.getUserByEmail(email)
            ));

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/currentUser")
    public ResponseEntity<ApiResponse> getCurrentUser() {
        try {
            return ResponseEntity.ok(new ApiResponse(
                    true,

                    userService.getLoggedInUser()
            ));

        } catch (ResourceNotFoundException e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @PostMapping(path = "/registerUser/Admin")
    public ResponseEntity<ApiResponse> createUserAsAdmin(@Valid @RequestBody SignUpDto dto) {
        try {

            User createdUser = new User();
            createdUser.setEmail(dto.getEmail());
            createdUser.setPassword(HashUtil.hashPassword(dto.getPassword()));
            createdUser.setFirstName(dto.getFirstName());
            createdUser.setLastName(dto.getLastName());
            createdUser.setRole(roleService.getRoleByName(dto.getRole()));
            createdUser.setDob(dto.getDob());
            createdUser.setGender(dto.getGender());
            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", userService.createUser(createdUser)));
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @PostMapping(path = "/registerUser/Customer")
    public ResponseEntity<ApiResponse> createUserAsCustomer(@Valid @RequestBody SignUpDto dto) {
        try{

        if (dto.getRole().equals(ERole.ADMIN)) {
            return ResponseEntity.ok(new ApiResponse(false, "User of role " + dto.getRole() + " can not be created here"));
        }
        User createdUser = new User();
        createdUser.setEmail(dto.getEmail());
        createdUser.setPassword(HashUtil.hashPassword(dto.getPassword()));
        createdUser.setFirstName(dto.getFirstName());
        createdUser.setLastName(dto.getLastName());
        createdUser.setRole(roleService.getRoleByName(dto.getRole()));
        createdUser.setDob(dto.getDob());
        createdUser.setGender(dto.getGender());
        return ResponseEntity.ok(new ApiResponse(true, "User created Successfully", userService.createUser(createdUser)));
        }
        catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<com.ne.template.utils.ApiResponse> deleteUser(@PathVariable(value = "id") UUID userId) {
        try {

            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully", this.userService.deleteUser(userId)));
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable(value = "id") UUID userId, @RequestBody User user) {
        return ResponseEntity.ok(new ApiResponse(true, this.userService.updateUser(user)));
    }


}
