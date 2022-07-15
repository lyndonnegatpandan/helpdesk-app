package com.example.helpdeskspring.Security;

import com.example.helpdeskspring.Model.Users;
import com.example.helpdeskspring.Repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UsersRepository usersRepository;

    public UserPrincipalDetailsService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = this.usersRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal(users);

        return userPrincipal;
    }
}
