package com.patsi.controller;

import com.patsi.bean.LogInSession;
import com.patsi.bean.Person;
import com.patsi.bean.UserLogin;
import com.patsi.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin

public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public String checkLogIn(@RequestBody UserLogin userLogin) {
        return loginService.checkLogIn(userLogin);
    }

    @PostMapping("/logout")
    public boolean logOut(String token) {
        loginService.logOut(token);
        return true;
    }

//    @DeleteMapping
//    public boolean logout(@RequestBody String userId) {
//        return loginService.logout(userId);
//    }

//    @GetMapping
//    public HttpServletResponse test(HttpServletRequest request, HttpServletResponse rsp) {
//        rsp.addCookie(new Cookie("ssoToke", "get token"));
//
//        return new HttpServletResponse();
//    }

}
