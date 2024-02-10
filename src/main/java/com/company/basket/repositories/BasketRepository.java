package com.company.basket.repositories;

import com.company.basket.entities.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, UUID> {

    Optional<BasketEntity> findByBookIdAndUserIdAndVisibilityTrue(UUID uuid, UUID currentUserId);

    List<BasketEntity> findAllByUserIdAndCartStatusFalseAndVisibilityTrue(UUID currentUserId);
    List<BasketEntity> findAllByUserIdAndCartStatusTrueAndVisibilityTrue(UUID currentUserId);

    Optional<BasketEntity> findByIdAndVisibilityTrue(UUID id);
}
