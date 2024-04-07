import com.patsi.Main;
import com.patsi.bean.LogInSession;
import com.patsi.bean.Person;
import com.patsi.bean.UserLogin;
import com.patsi.repository.PersonRepository;
import com.patsi.repository.SessionRepository;
import com.patsi.service.LogInSessionService;
import com.patsi.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

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

    //Constants
    final String targetUid = "Patsi";
    final UUID targetPersonId = UUID.randomUUID();
    final Person targetPerson = new Person(targetPersonId, "Patsi", "Patsi",
        "patsi@gmail.com","PatsiSmartHome", "PatsiSmartHome");
    final LogInSession expectedLoginSession = new LogInSession();
    final String expectedToken = "test-token-123";

    @BeforeEach
    void setUp(){
        when(logInSessionService.findPerson(targetUid))
            .thenReturn(new Person());
        when(personRepository.findByUserId("Patsi"))
            .thenReturn(Optional.of(targetPerson));
        when(sessionRepository.findByCustomerId(targetPersonId))
            .thenReturn(Optional.of(expectedLoginSession));
        when(logInSessionService.createUserToken())
            .thenReturn(expectedToken);
    }

    @Test
    void testCheckLogInIfValidUserIdAndPassword() {
        UserLogin validUser = new UserLogin(targetUid, "PatsiSmartHome");
        assertEquals("test-token-123", loginService.checkLogIn(validUser));
    }

    @Test
    void testCheckLogInIfInvalidUserIdAndPassword() {
        UserLogin user2 = new UserLogin("Batmy", "WrongPassword");
        assertEquals(null, loginService.checkLogIn(user2));

    }
}
