package com.patsi.bean;

import jakarta.persistence.*;

import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    @Column(name = "USERID")
    private String userId;
    private String name;
    private String email;
    private String password;

}
