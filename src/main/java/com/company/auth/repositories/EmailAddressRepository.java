package com.company.auth.repositories;


import com.company.auth.entities.EmailAddressEntity;
import com.company.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAddressRepository extends JpaRepository<EmailAddressEntity, BaseEntity> {
    EmailAddressEntity getByEmail(String email);
}
