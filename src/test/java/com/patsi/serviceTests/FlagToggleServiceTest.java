package com.patsi.serviceTests;

import com.patsi.Main;
import com.patsi.service.FlagToggleServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertFalse;


@SpringBootTest(classes = Main.class)
public class FlagToggleServiceTest {

    @Value("${enableEmail}")
    boolean enableEmail;

    //InjectMock will create new bean out of the context, thus autowired
    @Autowired
    private FlagToggleServices flagToggleServices;

    @Test
    void testGetEnableEmailFlagWithTrueValue() {
        boolean result = flagToggleServices.getEnableEmailFlag();
        assertFalse(result);
    }
}
