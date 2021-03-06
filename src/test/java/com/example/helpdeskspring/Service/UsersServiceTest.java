package com.example.helpdeskspring.Service;

import com.example.helpdeskspring.Model.Employee;
import com.example.helpdeskspring.Model.Role;
import com.example.helpdeskspring.Model.Users;
import com.example.helpdeskspring.Repository.EmployeeRepository;
import com.example.helpdeskspring.Repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    private UsersService usersServiceTest;

    @BeforeEach
    void setUp(){
        usersServiceTest = new UsersService(usersRepository, passwordEncoder);
    }

    @Test
    void getUsers() {
        usersServiceTest.getUsers();
        verify(usersRepository).findAll();
    }

    @Test
    void createUser() {
        Users user = new Users("username", "password", "USER");
        usersServiceTest.createUser(user);
        ArgumentCaptor<Users> usersArgumentCaptor = ArgumentCaptor.forClass(Users.class);
        verify(usersRepository).save(usersArgumentCaptor.capture());
        Users capturedUser = usersArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void updateUser() throws Exception {
        Users user = new Users("username", "password", "USER");
        given(usersRepository.findById(user.getUserId())).willReturn(Optional.of(user));
        usersServiceTest.updateUser(user.getUserId(), user);
        verify(usersRepository).save(user);
    }

    @Test
    void deleteUser() throws Exception{
        Users user = new Users("username", "password", "USER");
        given(usersRepository.findById(user.getUserId())).willReturn(Optional.of(user));
        Boolean test =  usersServiceTest.deleteUser(user.getUserId());
        verify(usersRepository).deleteById(user.getUserId());
        assertTrue(test);
    }



}