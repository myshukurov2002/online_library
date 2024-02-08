package com.company.attach;


import com.company.attach.entity.AttachEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttachRepository extends JpaRepository<AttachEntity, UUID> {
    List<AttachEntity> findAllByVisibilityTrue();
}
