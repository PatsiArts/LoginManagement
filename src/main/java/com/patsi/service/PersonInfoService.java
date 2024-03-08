package com.patsi.service;

import com.patsi.bean.Person;
import com.patsi.interceptors.LoggingInterceptor;
import com.patsi.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonInfoService {

    @Autowired
    PersonRepository personRepository;
    Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

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
        System.out.println("In service person userId " + person.getUserId());
        if (getPerson(person.getUserId())) {
            return false;
        } else {
            personRepository.save(person);
            return true;
        }
    }

    public void deletePeron(Person person) {
        System.out.println("Service");
        System.out.println(person);
        System.out.println(person.getUid());
        personRepository.deleteById(person.getUid());
    }
}
