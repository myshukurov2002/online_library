package com.company.auth.services;

import com.company.auth.dtos.AuthDto;
import com.company.auth.dtos.JwtResponse;
import com.company.auth.dtos.RegistrDto;
import com.company.auth.repositories.EmailHistoryRepository;
import com.company.base.ApiResponse;
import com.company.config.i18n.MessageService;
import com.company.config.security.utils.jwtUtil;
import com.company.config.security.utils.md5Util;
import com.company.expections.exp.ItemNotFoundException;
import com.company.expections.exp.UnAuthorizedException;
import com.company.user.entities.UserEntity;
import com.company.user.enums.Role;
import com.company.user.enums.Status;
import com.company.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final MessageService messageService;
    private final EmailHistoryRepository emailHistoryRepository;
    private final EmailSenderService emailSenderService;

    public ApiResponse<JwtResponse> login(AuthDto auth) {

        UserEntity user = userRepository
                .findByEmailAndVisibilityTrue(auth.email())
                .orElseThrow(ItemNotFoundException::new);

        if (user.getStatus().equals(Status.REGISTRATION)) {
            throw new UnAuthorizedException("unauthorized");
        }

        if (user.getPassword().equals(md5Util.encode(auth.password()))) {
            log.warn("login " + auth.email());

            return new ApiResponse<>(true, toDto(user));
        }
        return new ApiResponse<>(false, messageService.getMessage("incorrect.password"));
    }

    @Transactional(rollbackOn = Exception.class)
    public ApiResponse<?> registr(RegistrDto reg) {

        userRepository
                .findByEmailAndVisibilityTrue(reg.email())
                .ifPresent(
                        user -> {
                            if (user.getStatus().equals(Status.REGISTRATION)) {
                                user.setVisibility(false);
                                userRepository.save(user);
                            }
                        }
                );

        Long count = emailHistoryRepository
                .countAllByEmailAndCreatedDateAfter(reg.email(), LocalDateTime.now().minusMinutes(1));
        if (count > 4) {
            return new ApiResponse<>(false, messageService.getMessage("try.again.around.1minute"));
        }

        UserEntity user = toEntity(reg);
        userRepository.save(user);

        return emailSenderService.sendEmailVerification(reg.email());
    }

    private JwtResponse toDto(UserEntity entity) {

        List<Role> roles = entity
                .getRoles()
                .stream()
                .toList();

        return JwtResponse.builder()
                .id(entity.getId())
                .roles(roles)
                .token(jwtUtil.encode(entity.getId(), roles))
                .build();
    }

    private UserEntity toEntity(RegistrDto reg) {

        return UserEntity.builder()
                .firstName(reg.firstName())
                .lastName(reg.lastName())
                .email(reg.email())
                .roles(Set.of(Role.USER))
                .password(md5Util.encode(reg.password()))
                .status(Status.REGISTRATION)
                .build();
    }
}
