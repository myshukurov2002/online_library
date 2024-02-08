package com.company.config.security.details;

import com.company.user.entities.UserEntity;
import com.company.user.enums.Role;
import com.company.user.enums.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;
    private final List<GrantedAuthority> userRoleList;

    public CustomUserDetails(UserEntity user) {

        this.user = user;
        List<GrantedAuthority> list = new LinkedList<>();
        for (Role role : user.getRoles()) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
        }
        userRoleList = list;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoleList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getVisibility().equals(true);
    }

}
