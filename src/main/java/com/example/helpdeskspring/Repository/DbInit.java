package com.example.helpdeskspring.Repository;

import com.example.helpdeskspring.Model.Users;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        Users John = new Users("admin",passwordEncoder.encode("admin123"),"ADMIN");
        Users Doe = new Users("user", passwordEncoder.encode("user123"), "USER");

        List<Users> users = Arrays.asList(John, Doe);

        usersRepository.saveAll(users);
    }
}
