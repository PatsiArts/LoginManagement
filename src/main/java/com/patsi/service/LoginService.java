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

    public String checkLogIn(String userId, String logInPasswordHashed) {
        if (logInSessionService.findPerson(userId) != null) {
            Person p = personRepository.findByUserId(userId).orElse(null);
            if (p.getLogInPasswordHashed().equals(logInPasswordHashed)) {
                System.out.println("Successfully authenticated!");
                String tmptoken = logInSessionService.createUserToken();
                Long expiryTime = System.currentTimeMillis() + 600000L ;
                if (sessionRepository.findByCustomerId(p.getUid()).isPresent()) {
                    logInSessionService.endSession(p.getUid());
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

//    public boolean logout(@RequestBody String userId) {
//        return logInSessionService.endSession(userId);
//    }


}
