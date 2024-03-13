package com.patsi.repository;

import com.patsi.bean.LogInSession;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends Repository<LogInSession, UUID> {
    LogInSession save(LogInSession logInSession);

    List<LogInSession> findAll();

    LogInSession findBySessionToken(String token);

    Optional<LogInSession> findByCustomerId(UUID customerId);

    void deleteByCustomerId(UUID customerId);
}
