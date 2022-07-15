package com.example.helpdeskspring.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="role")
    private String role;

    public Users(){
        super();
    }

    public Users(String username, String password, String role){
        super();
        this.username = username;
        this.password = password;
        this.role=role;
    }

    public long getUserId(){
        return userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role=role;
    }

    @JsonIgnore
    public List<String> getRoleList(){
        if(this.role.length()>0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }


}