import com.patsi.Main;
import com.patsi.bean.LogInSession;
import com.patsi.bean.Person;
import com.patsi.bean.UserLogin;
import com.patsi.repository.PersonRepository;
import com.patsi.repository.SessionRepository;
import com.patsi.service.LogInSessionService;
import com.patsi.service.LoginService;
import com.patsi.utils.DateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest(classes = Main.class)
@ActiveProfiles("test")
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private PersonRepository personRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private LogInSessionService logInSessionService;

    private MockedStatic<DateHelper> mockDateHelper = mockStatic(DateHelper.class);

    //Login
    final String validUID = "Patsi";
    final UUID validPersonId = UUID.randomUUID();
    final Person validPerson = new Person(validPersonId, "Patsi", "Patsi",
        "patsi@gmail.com","PatsiSmartHome", "PatsiSmartHome");
    final LogInSession expectedLoginSession = new LogInSession();
    final String expectedToken = "test-token-123";
    final Date currentDate = new Date();
    //Logout
    final String validToken = "validToken";
    final String invalidToken = "invalidToken";


    @BeforeEach
    void setUp(){
        when(logInSessionService.findPerson(validUID))
            .thenReturn(new Person());
        when(personRepository.findByUserId("Patsi"))
            .thenReturn(Optional.of(validPerson));
        when(sessionRepository.findByCustomerId(validPersonId))
            .thenReturn(Optional.of(expectedLoginSession));
        when(logInSessionService.createUserToken())
            .thenReturn(expectedToken);
        when(DateHelper.getCurrentDate()).thenReturn(currentDate);
        when(logInSessionService.endSessionByToken(any()))
            .thenReturn(true);
    }

    //Login
    @Test
    void testCheckLogInIfValidUserIdAndPassword() {
        UserLogin validUser = new UserLogin(validUID, "PatsiSmartHome");
        assertEquals(expectedToken, loginService.checkLogIn(validUser));
        verify(logInSessionService).endSession(validPersonId);
        verify(logInSessionService)
            .createSession(validPersonId, expectedToken, currentDate.getTime() + 600000L);
    }
    @Test
    void testCheckLogInIfInvalidUserIdAndPasswordWithNoExistingSession() {
        when(sessionRepository.findByCustomerId(validPersonId))
            .thenReturn(Optional.empty());
        UserLogin user2 = new UserLogin(validUID, "PatsiSmartHome");
        assertEquals(expectedToken, loginService.checkLogIn(user2));
        verify(logInSessionService).findPerson(any());
        verify(logInSessionService, never()).endSession(validPersonId);
    }

    @Test
    void testCheckLogInIfInvalidUserIdAndPassword() {
        UserLogin user2 = new UserLogin("targetUid", "WrongPassword");
        assertEquals(null, loginService.checkLogIn(user2));
        verify(logInSessionService).findPerson(any());
        verifyNoMoreInteractions(logInSessionService);
    }

    //Logout
    @Test
    void testLogoutWithValidToken(){
        assertEquals(true, loginService.logOut(validToken));
        verify(logInSessionService).endSessionByToken(validToken);
    }
    @Test
    void testLogoutWithInvalidToken(){
        assertEquals(true, loginService.logOut(invalidToken));
        verify(logInSessionService).endSessionByToken(invalidToken);
    }
}
