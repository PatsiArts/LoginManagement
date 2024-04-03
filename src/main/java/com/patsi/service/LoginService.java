package com.patsi.service;

import com.patsi.bean.Person;
import com.patsi.bean.LogInSession;
import com.patsi.bean.UserLogin;
import com.patsi.repository.PersonRepository;
import com.patsi.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger log = LoggerFactory.getLogger(LoginService.class);

    public String checkLogIn(UserLogin user) {
        if (logInSessionService.findPerson(user.getUserId()) != null) {
            Person p = personRepository.findByUserId(user.getUserId()).orElse(null);
            if (p.getLogInPasswordHashed().equals(user.getLogInPasswordHashed())) {
                log.info("Successfully authenticated!");
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

    public boolean logOut(String token) {
        log.info("In logOut");
        logInSessionService.endSessionByToken(token);
        return true;
    }


}
