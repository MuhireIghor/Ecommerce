package com.ne.template.controllers;

import com.ne.template.dtos.requests.RegisterRoleDto;
import com.ne.template.enums.ERole;
import com.ne.template.models.Role;
import com.ne.template.repositories.IRoleRepository;
import com.ne.template.services.IRoleService;
import com.ne.template.utils.ApiResponse;
import com.ne.template.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final IRoleService roleService;
    private final IRoleRepository roleRepository;

    @Autowired
    public RoleController(IRoleService roleService, IRoleRepository roleRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/registerRole")
    public ResponseEntity<ApiResponse> registerRole(@RequestBody RegisterRoleDto dto) {
        try {
            Optional<Role> isRoleExistent = roleRepository.findByRoleName(dto.getRoleName());
            if (isRoleExistent.isPresent()) {
                return ResponseEntity.ok(
                        new ApiResponse(false, "Role already existent")
                );
            }
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Role created successfully",
                    roleService.registerRole(dto.getRoleName())
            ));

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

    @GetMapping("/allRoles")
    public ResponseEntity<ApiResponse> getAllRoles() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Roles fetched Successfully",
                            roleService.getAllRoles()

                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }


    }
}
