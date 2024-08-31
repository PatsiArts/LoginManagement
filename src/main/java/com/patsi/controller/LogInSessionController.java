package com.patsi.controller;

import com.patsi.bean.Person;
import com.patsi.service.LogInSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/logInSession")
@CrossOrigin
public class LogInSessionController {

    @Autowired
    private LogInSessionService logInSessionService;

    @GetMapping
    public Person findPersonByToken(@RequestHeader(AUTHORIZATION) String token) {
        return logInSessionService.findPersonByToken(token);
    }

    @GetMapping("/getPersonUid")
    public ResponseEntity<String> findPersonUidByToken(@RequestHeader(AUTHORIZATION) String token) {
        Person person = logInSessionService.findPersonByToken(token);
        return (person != null) ? new ResponseEntity<>(person.getUid().toString(), HttpStatus.OK)
            : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    @PutMapping
    public boolean renewToken(@RequestParam String token) {
        return logInSessionService.renewSession(token);
    }
}
