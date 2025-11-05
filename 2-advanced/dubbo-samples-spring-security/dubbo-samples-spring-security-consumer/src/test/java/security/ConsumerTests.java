/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
