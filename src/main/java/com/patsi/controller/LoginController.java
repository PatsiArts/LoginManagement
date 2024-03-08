package com.patsi.controller;

import com.patsi.bean.LogInSession;
import com.patsi.bean.Person;
import com.patsi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin

public class LoginController {

    @Autowired
    private LoginService loginService;


    @PutMapping
    public String checkLogIn(@RequestParam("userId") String userId, @RequestParam String passwordHashed) {
        return loginService.checkLogIn(userId, passwordHashed);
    }

    @DeleteMapping
    public boolean logout(@RequestBody String userId) {
        return loginService.logout(userId);
    }


}
