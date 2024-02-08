package com.company.cart.repositories;

import com.company.cart.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {

    Optional<CartEntity> findByBookIdAndUserIdAndVisibilityTrue(UUID uuid, UUID currentUserId);

    List<CartEntity> findAllByUserIdAndCartStatusFalseAndVisibilityTrue(UUID currentUserId);
    List<CartEntity> findAllByUserIdAndCartStatusTrueAndVisibilityTrue(UUID currentUserId);

    Optional<CartEntity> findByIdAndVisibilityTrue(UUID id);
}
