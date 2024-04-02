package com.patsi.service;

import com.patsi.bean.LogInSession;
import com.patsi.bean.Person;
import com.patsi.repository.PersonRepository;
import com.patsi.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class LogInSessionService {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Autowired
    PersonRepository personRepository;
    @Autowired
    SessionRepository sessionRepository;

    public boolean createSession(UUID UID, String token, Long expiryTime) {
        LogInSession logInSession = new LogInSession(UID, token, expiryTime);
        sessionRepository.save(logInSession);
        return true;
    }
    public List<LogInSession> findAllSessions() {
        return sessionRepository.findAll();
    }
    @Transactional
    public boolean endSession(UUID uid) {
        sessionRepository.deleteByCustomerId(uid);
        return false;
    }

    @Transactional
    public boolean endSessionByToken(String token) {
        sessionRepository.deleteBySessionToken(token);
        return true;
    }

    public Person findPersonByToken(String token) {
        String sessionToken = token.substring(7);
        LogInSession s = sessionRepository.findBySessionToken(sessionToken);
        if (s != null) {
            return personRepository.findById(s.getCustomerId()).orElse(null);
        } else {
            return null;
        }
    }

    public Person findPerson(String userId) {
        return personRepository.findByUserId(userId).orElse(null);
    }

    public boolean renewSession(String token) {
        LogInSession s = sessionRepository.findBySessionToken(token);
        Long expiryTime = System.currentTimeMillis() + 600000L;
        s.setExpiryTime(expiryTime);
        return true;
    }

    public String createUserToken () {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    };

}
