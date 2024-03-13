package com.patsi.controller;

import com.patsi.bean.LogInSession;
import com.patsi.bean.Person;
import com.patsi.service.LogInSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logInSession")
@CrossOrigin

public class LogInSessionController {
    @Autowired
    LogInSessionService logInSessionService;

    @PostMapping
    public ResponseEntity<String> findPersonByToken(@RequestBody String token) {
        System.out.println("Got it" +token);
        Person p = logInSessionService.findPersonByToken(token);
        return (p != null) ? new ResponseEntity<>(p.getUid().toString(), HttpStatus.OK)
            : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    @GetMapping
    public List<LogInSession> findAllSessions() {
        return logInSessionService.findAllSessions();
    }

    @PutMapping
    public boolean renewToken(String token) {
        return logInSessionService.renewSession(token);
    }
}
