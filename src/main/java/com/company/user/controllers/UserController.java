package com.company.user.controllers;

import com.company.base.ApiResponse;
import com.company.user.dtos.RoleCr;
import com.company.user.dtos.UserResp;
import com.company.user.enums.Role;
import com.company.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/add-roles")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResp>> addRoleToUser(
            @RequestBody RoleCr dto) {
        return ResponseEntity.ok(userService.addRoleToUser(dto));
    }


    @DeleteMapping("/delete-roles")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResp>> deleteRoleFromUser(
            @RequestBody RoleCr dto) {
        return ResponseEntity.ok(userService.deleteRoleFromUser(dto));
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResp>> getById(
            @PathVariable UUID id
            ) {
        return ResponseEntity.ok(userService.getById(id));
    }
    @GetMapping("/get-all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResp>>> getAllUser() {
        return ResponseEntity.ok(userService.getByAll());
    }

    @GetMapping("/get-roles")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Role[]>> roles() {
        return ResponseEntity.ok(userService.roles());
    }

}
