package com.example.helpdeskspring.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Builder
@Entity
@Table(name = "employee", uniqueConstraints = @UniqueConstraint(columnNames = {"employee_number"}))
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="employee_number", nullable = false, unique = true, updatable = false)
    private long employeeNumber;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="middle_name", nullable = false)
    private String middleName;

    @Column(name="last_name", nullable = false)
    private String lastName;



    @Column(name="department", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "assignee", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    Set<Ticket> ticketAssigned= new HashSet<>();

    @ManyToMany(mappedBy = "watcher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    Set<Ticket> ticketWatched = new HashSet<>();




    public Employee(){
        super();
    }

    public Employee(long employeeNumber, String firstName, String middleName, String lastName, Department department, Set<Ticket> ticketAssigned, Set<Ticket> ticketWatched){
        super();
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.department = department;
        this.ticketAssigned = ticketAssigned;
        this.ticketWatched = ticketWatched;

    }



    public long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public long getEmployeeNumber(){
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber){
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getMiddleName(){
        return middleName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }

    public Department getDepartment(){
        return department;
    }

    public void setDepartment(Department department){
        this.department = department;
    }

    public Set<Ticket> getTicketAssigned(){
        return ticketAssigned;
    }

    public void setTicketAssigned(Set<Ticket> ticketAssigned){
        this.ticketAssigned=ticketAssigned;
    }
    public Set<Ticket> getTicketWatched() {
        return ticketWatched;
    }

    public void setTicketWatched(Set<Ticket> ticketWatched) {
        this.ticketWatched = ticketWatched;
    }



}