package com.patsi.controller;

import com.patsi.bean.Person;
import com.patsi.service.LogInSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logInSession")
@CrossOrigin

public class LogInSessionController {
    @Autowired
    LogInSessionService logInSessionService;

    @GetMapping
    public ResponseEntity<Person> findPersonByToken(String token) {
        Person p = logInSessionService.findPersonByToken(token);
        return (p != null) ? new ResponseEntity<>(p, HttpStatus.OK)
            : new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    public boolean renewToken(String token) {
        return logInSessionService.renewSession(token);
    }
}
