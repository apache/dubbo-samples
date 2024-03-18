package security;

import org.apache.dubbo.samples.security.ConsumerApplication;
import org.apache.dubbo.samples.security.api.UserService;
import org.apache.dubbo.samples.security.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ConsumerApplication.class)
@ExtendWith(MockitoExtension.class)
class ConsumerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private ConsumerApplication consumerApplication;

    @Test
    void testFindByUsername() {
        // Arrange
        String username = "testUser";
        User expectedUser = new User();
        expectedUser.setGender("male");
        expectedUser.setUserName(username);
        expectedUser.setId("123456");
        when(userService.findByUsername(username)).thenReturn(expectedUser);

        // Act
        User actualUser = userService.findByUsername(username);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userService, times(1)).findByUsername(username);
    }

    @Test
    void testQueryAll() {
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User());
        when(userService.queryAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.queryAll();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userService, times(1)).queryAll();
    }
}
