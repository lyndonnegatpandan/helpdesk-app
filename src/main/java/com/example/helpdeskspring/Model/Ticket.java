package com.example.helpdeskspring.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticket;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "severity")
    private Severity severity;

    @Column(name = "status")
    private Status status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "assigneeId", insertable = true, updatable = true)
    private Employee assignee;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "ticket_watcher", joinColumns = {@JoinColumn(name="ticket_id")}, inverseJoinColumns = {@JoinColumn(name="id")})
    private List <Employee> watcher = new ArrayList<>();

    public Ticket(){
        super();
    }

    public Ticket(String title, String description, Severity severity, Status status){
        super();
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
    }

    public long getTicket(){
        return ticket;
    }

    public void setTicket(Long ticket){
        this.ticket = ticket;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Severity getSeverity(){
        return severity;
    }

    public void setSeverity(Severity severity){
        this.severity = severity;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Employee getAssignee(){
        return assignee;
    }

    public void setAssignee(Employee assignee){
        this.assignee = assignee;
    }

    public List<Employee> getWatcher(){
        return watcher;
    }

    public void setWatcher(List<Employee> watcher){
        this.watcher = watcher;
    }
}