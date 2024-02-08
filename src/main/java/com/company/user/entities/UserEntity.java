package com.company.user.entities;

import com.company.base.BaseEntity;
import com.company.user.enums.Role;
import com.company.user.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @Email
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(
            EnumType.STRING
    )
    private Set<Role> roles;

    @Column
    @Enumerated(
            EnumType.STRING
    )
    private Status status = Status.REGISTRATION;
}
