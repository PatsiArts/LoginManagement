package com.patsi.service;

import com.common.email.bean.Email;
import com.common.email.service.EmailService;
import com.patsi.bean.Person;
import com.patsi.repository.PersonRepository;
import com.patsi.utils.SHAHelper;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.patsi.utils.SHAHelper.bytesToHex;


@Service
public class PersonInfoService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private LoginValueServices loginValueServices;
    @Autowired
    private FlagToggleServices flagToggleServices;
    Logger log = LoggerFactory.getLogger(PersonInfoService.class);


//    private String algorithm = "SHA3-256";

    //Check if useId exists
    public boolean getPerson(String userId) {
        return (personRepository.findByUserId(userId).isPresent()) ? true : false;
    }

    public List<Person> getAllPerson() {
        log.info("Inside Get All Person");
        return personRepository.findAll();
    }

    //Encrypt Password before store to DB
    private String passwordEncryption(String password) {
        log.info("In Password Encryption");
        byte[] passwordByte = password.getBytes(SHAHelper.UTF_8);
        byte[] saltBytes = loginValueServices.getSaltProperties().getBytes(StandardCharsets.UTF_8);
        byte[] passwordWithSalt = ByteBuffer.allocate(
                saltBytes.length + passwordByte.length)
            .put(saltBytes)
            .put(passwordByte)
            .array();
        byte[] dm = SHAHelper.digest(passwordWithSalt, loginValueServices.getAlgorithmProperties());
        return SHAHelper.bytesToHex(dm);
    }

    //Register Person
    public boolean registerPerson(Person person) throws MessagingException {
        log.info("In service person userId " + person.getUserId());
        if (getPerson(person.getUserId())) {
            return false;
        } else {
            person.setPassword(passwordEncryption(person.getPassword()));
            personRepository.save(person);
            if (flagToggleServices.getEnableEmailFlag()) {
                sendEmailTest(person);
            }
            return true;
        }
    }

    public boolean sendEmailTest(Person p) throws MessagingException {
        Email sighUpEmail = new Email(p.getEmail(), "Patsi",
            "Sign Up Notification", "Welcome to Smart Home", true);
        emailService.sendEmail(sighUpEmail);
        return true;
    }

    public void deletePeron(Person person) {
        personRepository.deleteById(person.getUid());
    }
}
