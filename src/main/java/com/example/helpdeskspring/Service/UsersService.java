package com.example.helpdeskspring.Service;


import com.example.helpdeskspring.Model.Users;
import com.example.helpdeskspring.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Users> getUsers(){
        return usersRepository.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Users createUser(Users users){
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
    public void deleteUser(long userId){
        Users users = usersRepository.findById(userId).get();
        usersRepository.deleteById(userId);
    }


}