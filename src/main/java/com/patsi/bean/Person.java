package com.patsi.bean;

import com.common.validation.annotations.IsEmail;
import com.common.validation.annotations.IsPassword;
import com.patsi.annotations.IsUserName;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotEmpty
    @Column(name = "USERID")
    private String userId;
    @NotNull
    @NotEmpty
    @IsUserName
    private String name;
    @NotNull
    @NotEmpty
    @IsEmail
    private String email;
    @NotNull
    @NotEmpty
    @IsPassword(min = 4)
    private String password;

}
