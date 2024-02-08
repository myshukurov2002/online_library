package com.company.user.repositories;

import com.company.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    List<UserEntity> findAllByVisibilityTrue();

    Optional<UserEntity> findByIdAndVisibilityTrue(UUID uuid);

    Optional<UserEntity> findByEmailAndVisibilityTrue(String toAccount);

//    UserAddressMapper getAddress(UUID id);
}
