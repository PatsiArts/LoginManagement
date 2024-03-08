package com.patsi.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class LogInSession {
    private UUID customerID;
    @Id
    @Column(nullable = false)
    private String sessionToken;
    @Column(nullable = false)
    private Long expiryTime;

    public LogInSession() {
    }

    public LogInSession(UUID customerID, String sessionToken, Long expiryTime) {
        this.customerID = customerID;
        this.sessionToken = sessionToken;
        this.expiryTime = expiryTime;
    }

    public UUID getCustomerID() {
        return customerID;
    }

    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }
}
