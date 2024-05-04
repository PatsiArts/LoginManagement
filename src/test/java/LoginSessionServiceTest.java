import com.patsi.Main;
import com.patsi.bean.LogInSession;
import com.patsi.bean.Person;
import com.patsi.repository.PersonRepository;
import com.patsi.repository.SessionRepository;
import com.patsi.service.LogInSessionService;
import com.patsi.utils.TokenHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = Main.class)
@ActiveProfiles("test")
public class LoginSessionServiceTest {

    @InjectMocks
    private LogInSessionService logInSessionService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private SessionRepository sessionRepository;

    final UUID uid = UUID.randomUUID();
    final String token = "Bearer Token";
    final Long expiryTime = System.currentTimeMillis() + 600000L;
    LogInSession logInSession = new LogInSession(uid, token, expiryTime);
    Person person = new Person(uid, "userId", "name", "unitTest@gmail.com",
        "password");

    @BeforeEach
    void setUp() {
        //Find session by token
        when(sessionRepository.findBySessionToken("Token"))
            .thenReturn(logInSession);
        //Find Person by customerId
        when(personRepository.findById(logInSession.getCustomerId()))
            .thenReturn(java.util.Optional.ofNullable(person));
        //Find Person by userId
        when(personRepository.findByUserId("userId"))
            .thenReturn(java.util.Optional.ofNullable(person));
        try (MockedStatic<TokenHelper> tokenHelper = Mockito.mockStatic(TokenHelper.class)) {
            tokenHelper.when(() -> TokenHelper.removeBearer(token))
                .thenReturn("Token");
        }
    }

    @Test
    void testCreateSessionWithValidId() {
        boolean result = logInSessionService.createSession(uid, token, expiryTime);
        assertEquals(true, result);
        LogInSession logInSession = new LogInSession(uid, token, expiryTime);
        verify(sessionRepository).save(logInSession);
    }

    @Test
    void testEndSession() {
        boolean result = logInSessionService.endSession(uid);
        assertEquals(false, result);
        verify(sessionRepository).deleteByCustomerId(uid);
    }

    @Test
    void testEndSessionByToken() {
        boolean result = logInSessionService.endSessionByToken(token);
        assertEquals(true, result);
        verify(sessionRepository).deleteBySessionToken(token);

    }

    @Test
    void testFindPersonByValidToken() {
        try (MockedStatic<TokenHelper> tokenHelper = Mockito.mockStatic(TokenHelper.class)) {
            tokenHelper.when(() -> TokenHelper.removeBearer(token))
                .thenReturn("Token");
        }
        Person result = logInSessionService.findPersonByToken(token);
        assertEquals(person, result);
    }

    @Test
    void testFindPersonByInValidToken() {
        Person result = logInSessionService.findPersonByToken("Invalid Token");
        assertEquals(null, result);
    }

    @Test
    void testFindPerson() {
        Person result = logInSessionService.findPerson("userId");
        assertEquals(person, result);
    }

    @Test
    void testFindPersonWithInvalidUserId() {
        Person result = logInSessionService.findPerson("inValidUserId");
        assertEquals(null, result);
    }

    @Test
    void renewSessionWithValidToken() {
        boolean result = logInSessionService.renewSession(token);
        verify(sessionRepository).findBySessionToken("Token");
        assertTrue(result);
    }

    @Test
    void renewSessionWithInvalidToken() {
        boolean result = logInSessionService.renewSession("invalidToken");
        verify(sessionRepository).findBySessionToken("invalidToken");
        assertEquals(false, result);
    }


}