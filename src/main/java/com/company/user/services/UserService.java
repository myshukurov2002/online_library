package com.company.user.services;

import com.company.base.ApiResponse;
import com.company.user.dtos.RoleCr;
import com.company.user.dtos.UserResp;
import com.company.user.enums.Role;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ApiResponse<UserResp> addRoleToUser(RoleCr dto);

    ApiResponse<UserResp> deleteRoleFromUser(RoleCr dto);

    ApiResponse<UserResp> getById(UUID id);

    ApiResponse<Role[]> roles();

    ApiResponse<List<UserResp>> getByAll();

}
