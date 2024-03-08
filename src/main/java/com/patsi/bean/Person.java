package com.patsi.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    private String userId;
    private String name;
    private String email;
    @Getter(AccessLevel.NONE) // Disable Lombok generation for getter
    private String logInName;
    private String logInPasswordHashed;

    public String getLogInName() {
        return userId;
    }

    public void setLogInName(String logInName) {
        this.logInName = userId;
    }

}
