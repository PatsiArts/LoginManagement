package com.patsi.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInSession {
    private UUID customerId;
    @Id
    @Column(nullable = false)
    private String sessionToken;
    @Column(nullable = false)
    private Long expiryTime;
}
