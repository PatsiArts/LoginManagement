package com.patsi.service;

import com.patsi.bean.Person;
import com.patsi.bean.UserLogin;
import com.patsi.repository.PersonRepository;
import com.patsi.repository.SessionRepository;
import com.patsi.utils.DateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private LogInSessionService logInSessionService;

    Logger log = LoggerFactory.getLogger(LoginService.class);

    public String checkLogIn(UserLogin user) {
        if (logInSessionService.findPerson(user.getUserId()) != null) {
            Person p = personRepository.findByUserId(user.getUserId()).orElse(null);
            if (p.getPassword().equals(user.getPassword())) {
                log.info("Successfully authenticated!");
                String tmpToken = logInSessionService.createUserToken();
                Long expiryTime = DateHelper.getCurrentDate().getTime() + 600000L;
                if (sessionRepository.findByCustomerId(p.getUid()).isPresent()) {
                    logInSessionService.endSession(p.getUid());
                    logInSessionService.createSession(p.getUid(), tmpToken, expiryTime);
                    return tmpToken;
                } else {
                    logInSessionService.createSession(p.getUid(), tmpToken, expiryTime);
                    return tmpToken;
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
