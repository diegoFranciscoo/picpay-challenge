package com.project.picpay.service;

import com.project.picpay.domain.user.User;
import com.project.picpay.domain.user.UserType;
import com.project.picpay.mapper.UserMapper;
import com.project.picpay.respository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;


    @Test
    @DisplayName("Should return user by id when successfully")
    void findById_ReturnUser_WhenSuccessfully() {
        var user = new User(1L, "testingUser", new BigDecimal(10), "1234567", "testing@gmail.com", "1234", UserType.MERCHANT);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User userById = userService.findById(1L);

        assertNotNull(userById);
        assertEquals(user.getId(), userById.getId());
        assertEquals(user.getCpf(), userById.getCpf());
        assertEquals(user.getEmail(), userById.getEmail());

    }

    @Test
    @DisplayName("Should create user when successfully")
    void saveUser_CreateUser_WhenSuccessfully() {
        var user = new User(1L, "testingUser", new BigDecimal(10), "1234567", "testing@gmail.com", "1234", UserType.MERCHANT);

        when(userRepository.save(any(User.class))).thenReturn(user);
        User userSaved = userService.saveUser(user);

        assertNotNull(userSaved);
        assertEquals(user.getId(), userSaved.getId());
        assertEquals(user.getCpf(), userSaved.getCpf());
        assertEquals(user.getEmail(), userSaved.getEmail());

    }

    @Test
    @DisplayName("Should create user with mapper when successfully")
    void save_CreateUserWithMapper_WhenSuccessfully() {
        var user = new User(1L, "testingUser", new BigDecimal(10), "1234567", "testing@gmail.com", "1234", UserType.MERCHANT);

        when(mapper.toUser(any())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        User userSaved = userService.saveUser(user);

        assertNotNull(userSaved);
        assertEquals(user.getId(), userSaved.getId());
        assertEquals(user.getCpf(), userSaved.getCpf());
        assertEquals(user.getEmail(), userSaved.getEmail());

    }

    @Test
    @DisplayName("Should return list of user when successfully")
    void listAll_ReturnListOfUser_WhenSuccessfully() {
        var user = new User(1L, "testingUser", new BigDecimal(10), "1234567", "testing@gmail.com", "1234", UserType.MERCHANT);
        var user2 = new User(2L, "testingUser2", new BigDecimal(20), "12345678", "testing2@gmail.com", "12345", UserType.COMMON);

        when(userRepository.findAll()).thenReturn(List.of(user, user2));
        List<User> users = userService.listAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(users.getFirst().getId(), user.getId());
        assertEquals(users.getLast().getId(), user2.getId());

    }


}