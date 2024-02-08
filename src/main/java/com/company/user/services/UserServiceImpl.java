package com.company.user.services;

import com.company.base.ApiResponse;
import com.company.config.i18n.MessageService;
import com.company.config.security.utils.securityUtil;
import com.company.expections.exp.ItemNotFoundException;
import com.company.user.dtos.RoleCr;
import com.company.user.dtos.UserResp;
import com.company.user.entities.UserEntity;
import com.company.user.enums.Role;
import com.company.user.mapper.UserDtoMapper;
import com.company.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MessageService messageService;
    private final UserDtoMapper userDtoMapper;

    @Override
    public ApiResponse<UserResp> addRoleToUser(RoleCr dto) {

        UserEntity user = get(dto.userId());

        assert user != null;
        Set<Role> userRoles = user.getRoles();

        userRoles.addAll(dto.roles());
        userRepository.save(user);

        log.info("roles added to " + user.getId() + " " + dto.roles());

        return new ApiResponse<>(true, messageService.getMessage("success.added.role"), userDtoMapper.apply(user));
    }

    @Override
    public ApiResponse<UserResp> deleteRoleFromUser(RoleCr dto) {

        UserEntity user = get(dto.userId());

        assert user != null;
        Set<Role> userRoles = user.getRoles();

        dto.roles()
                .forEach(userRoles::remove);

        userRepository.save(user);

        log.info("roles deleted from " + user.getId() + " " + dto.roles());

        return new ApiResponse<>(true, messageService.getMessage("success.deleted.role"), userDtoMapper.apply(user));
    }

    @Override
    public ApiResponse<UserResp> getById(UUID id) {
        return new ApiResponse<>(true, userDtoMapper.apply(get(id)));
    }

    @Override
    public ApiResponse<List<UserResp>> getByAll() {

        List<UserResp> list = userRepository
                .findAllByVisibilityTrue()
                .stream()
                .map(userDtoMapper)
                .toList();

        return new ApiResponse<>(true, list);
    }

    @Override
    public ApiResponse<Role[]> roles() {
        return new ApiResponse<>(true, Role.getAllRoles());
    }

    private UserEntity get(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

}