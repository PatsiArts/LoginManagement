package com.patsi.service;

import com.patsi.bean.Person;
import com.patsi.bean.LogInSession;
import com.patsi.repository.PersonRepository;
import com.patsi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoginService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    LogInSessionService logInSessionService;

    public String checkLogIn(String userId, String passwordHashed) {
        if (logInSessionService.findPerson(userId) != null) {
            Person p = personRepository.findByUserId(userId).orElse(null);
            if (p.getLogInPasswordHashed().equals(passwordHashed)) {
                System.out.println("Successfully authenticated!");
                String tmptoken = logInSessionService.createUserToken.get();
                Long expiryTime = System.currentTimeMillis() + 10000L;
                if (sessionRepository.findByCustomerID(p.getUid()).isPresent()) {
                    logInSessionService.endSession(p.getUid().toString());
                    logInSessionService.createSession(p.getUid(), tmptoken, expiryTime);
                    return tmptoken;
                } else {
                    logInSessionService.createSession(p.getUid(), tmptoken, expiryTime);
                    return tmptoken;
                }
            }
        }
        return null;
    }

    public boolean logout(@RequestBody String userId) {
        return logInSessionService.endSession(userId);
    }


}
