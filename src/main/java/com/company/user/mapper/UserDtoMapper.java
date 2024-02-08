package com.company.user.mapper;

import com.company.user.dtos.UserResp;
import com.company.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserDtoMapper implements Function<UserEntity, UserResp> {

    @Override
    public UserResp apply(UserEntity user) {

        return UserResp.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles())
                .build();
    }
}
