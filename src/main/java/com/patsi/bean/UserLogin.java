package com.patsi.bean;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {
    private String userId;
    private String logInPasswordHashed;
}
