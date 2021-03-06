package com.wedevol.springbootjunit4.core.service.impl;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.wedevol.springbootjunit5.core.dao.UserRepository;
import com.wedevol.springbootjunit5.core.entity.UserEntity;
import com.wedevol.springbootjunit5.core.service.impl.UserServiceImpl;

/**
 * Test the User Service Implementation: test the service logic
 *
 * @author Charz++
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final Long USER_ONE_ID = 1L;
    private static final String USER_ONE_ID_STR = "1";

    @Mock
    private UserRepository repoMock;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;

    @BeforeEach
    public void init() {
        userEntity = new UserEntity();
        userEntity.setId(USER_ONE_ID);
        userEntity.setName("Carlos");
        userEntity.setAge(26);
        userEntity.setEmail("carlos@yopmail.com");
    }

    @Test
    public void findOneAndUserExists() {
        // Data preparation
        Mockito.when(repoMock.findById(USER_ONE_ID_STR)).thenReturn(userEntity);

        // Method call
        UserEntity user = userService.findByIdOrThrow(USER_ONE_ID_STR);

        // Verification
        Assertions.assertNotNull(user);
        Mockito.verify(repoMock, Mockito.times(1)).findById(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
    public void findOneAndUserIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            // Method call
            UserEntity user = userService.findByIdOrThrow(USER_ONE_ID_STR);

            // Verification
            Assertions.assertNull(user);
            Mockito.verify(repoMock, Mockito.times(1)).findById(ArgumentMatchers.anyString());
            Mockito.verifyNoMoreInteractions(repoMock);
        });

    }

    @Test
    public void createUserAndUserAlreadyExists() {
        // Data preparation
        Mockito.when(repoMock.findByEmail("carlos@yopmail.com")).thenReturn(userEntity);

        Assertions.assertThrows(RuntimeException.class, () -> {
            // Method call
            UserEntity user = userService.create(userEntity);

            // Verification
            Assertions.assertNull(user);
            Mockito.verify(repoMock, Mockito.times(1)).findByEmail(ArgumentMatchers.anyString());
            Mockito.verifyNoMoreInteractions(repoMock);
        });

    }

    @Test
    public void updateUserAndUserNotExists() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            // Method call
            userService.update(USER_ONE_ID_STR, userEntity);

            // Verification
            Mockito.verify(repoMock, Mockito.times(1)).findByEmail(ArgumentMatchers.anyString());
            Mockito.verifyNoMoreInteractions(repoMock);
        });
    }

    @Test
    public void countAllUsers() {
        // Data preparation
        List<UserEntity> users = Arrays.asList(userEntity, userEntity, userEntity);
        Mockito.when(repoMock.countAll()).thenReturn(users.size());

        // Method call
        Integer usersQty = userService.countAll();

        // Verification
        Assertions.assertTrue(usersQty.intValue() == 3);
        Mockito.verify(repoMock, Mockito.times(1)).countAll();
        Mockito.verifyNoMoreInteractions(repoMock);
    }

}
