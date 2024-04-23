package com.patsi.service;

import com.common.bean.Email;
import com.common.service.EmailService;
import com.patsi.bean.Person;
import com.patsi.repository.PersonRepository;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonInfoService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EmailService emailService;
    Logger log = LoggerFactory.getLogger(PersonInfoService.class);


    //Check if useId exists
    public boolean getPerson(String userId) {
        return (personRepository.findByUserId(userId).isPresent()) ? true : false;
    }

    public List<Person> getAllPerson() {
        log.info("Inside Get All Person");
        return personRepository.findAll();
    }

    //Register Person
    public boolean registerPerson(Person person) {
        log.info("In service person userId " + person.getUserId());
        if (getPerson(person.getUserId())) {
            return false;
        } else {
            personRepository.save(person);
            return true;
        }
    }

    public boolean sendEmailTest() throws MessagingException {
        Email sighUpEmail = new Email("patsipatsichang@gmail.com",
            "Sign Up Notification", "Welcome to Smart Home", true);
        emailService.sendEmail(sighUpEmail);
        return true;
    }

    public void deletePeron(Person person) {
        personRepository.deleteById(person.getUid());
    }
}
