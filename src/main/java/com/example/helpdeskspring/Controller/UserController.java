package com.example.helpdeskspring.Controller;


import com.example.helpdeskspring.Model.Users;
import com.example.helpdeskspring.Repository.UsersRepository;
import com.example.helpdeskspring.Service.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UsersService usersService;

//    private final UsersRepository usersRepository;

    @Autowired
    public UserController(UsersService usersService){
        this.usersService = usersService;
        //this.usersRepository=usersRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<Users> getUsers(){
        return new ResponseEntity(usersService.getUsers(), HttpStatus.OK);
    }

    @PostMapping(path="/add")
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        return new ResponseEntity<>(usersService.createUser(users), HttpStatus.CREATED) ;
    }

    @PutMapping(path = "/update/{userId}")
    public Users updateUser(@PathVariable(value="userId") long userId, @RequestBody Users user1) throws Exception{
        return usersService.updateUser(userId, user1);
    }

    @DeleteMapping(path = "/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value="userId")long userId)throws Exception{
        usersService.deleteUser(userId);
    }


}