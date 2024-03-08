package com.patsi.repository;

import com.patsi.bean.Person;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends Repository<Person, UUID> {
    Person save(Person person);

    List<Person> findAll();

    Optional<Person> findById(UUID uid);

    Optional<Person> findByUserId(String userId);

    void deleteById(UUID UID);
}
