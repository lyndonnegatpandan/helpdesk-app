package com.example.helpdeskspring.Service;


import com.example.helpdeskspring.Model.Users;
import com.example.helpdeskspring.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Users> getUsers(){
        return usersRepository.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Users createUser(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersRepository.save(users);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Users updateUser(long userId, Users user1) throws Exception{
        Users users = usersRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        users.setUsername(user1.getUsername());
        users.setPassword(user1.getPassword());
        users.setRole(user1.getRole());

        final Users updatedUser = usersRepository.save(users);

        return updatedUser;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteUser(long userId) throws Exception {
        Users users = usersRepository.findById(userId).get();
        usersRepository.deleteById(userId);
        return true;
    }


}