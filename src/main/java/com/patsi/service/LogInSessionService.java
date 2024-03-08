package com.patsi.service;

import com.patsi.bean.LogInSession;
import com.patsi.bean.Person;
import com.patsi.repository.PersonRepository;
import com.patsi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean endSession(String userId) {
        personRepository.deleteById(findPerson(userId).getUid());
        return false;
    }

    public Person findPersonByToken(String token) {
        LogInSession s = sessionRepository.findBySessionToken(token);
        if (s != null) {
            return personRepository.findById(s.getCustomerID()).orElse(null);
        } else {
            return null;
        }
    }

    public Person findPerson(String userId) {
        return personRepository.findByUserId(userId).stream().findFirst().orElse(null);
    }

    public boolean renewSession(String token) {
        LogInSession s = sessionRepository.findBySessionToken(token);
        Long expiryTime = System.currentTimeMillis() + 10000L;
        s.setExpiryTime(expiryTime);
        return true;
    }

    Supplier<String> createUserToken = () -> {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    };

}
