package com.patsi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FlagToggleServices {

    @Value("${enableEmail}")
    boolean enableEmail;

    public boolean getEnableEmailFlag() {
        return enableEmail;
    }
}
