package com.example.helpdeskspring.Service;


import com.example.helpdeskspring.Model.Employee;
import com.example.helpdeskspring.Model.Ticket;
import com.example.helpdeskspring.Repository.EmployeeRepository;
import com.example.helpdeskspring.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EmployeeRepository employeeRepository){
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;

    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Ticket> getAllTicket(){
        return ticketRepository.findAll();
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Optional<Ticket> getTicketById(long ticket){
        return ticketRepository.findById(ticket);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Ticket createTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Ticket addAssignee(long ticketId, long id)throws Exception{
        Ticket ticket = ticketRepository.findById(ticketId).get();
        Employee employee = employeeRepository.findById(id).get();

        if(ticket.getAssignee()!=null){
            throw new Exception("There's an employee assigned to this ticket.");
        }

        ticket.setAssignee(employee);
        return ticketRepository.save(ticket);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Ticket addWatcher(long ticketId, long id){
        Ticket ticket = ticketRepository.findById(ticketId).get();
        Employee employee = employeeRepository.findById(id).get();
        ticket.getWatcher().add(employee);
        return ticketRepository.save(ticket);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteTicket(long ticket){
        ticketRepository.deleteById(ticket);
        return true;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Ticket updateTicket(long id, Ticket ticket1)throws Exception{

        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new Exception("Ticket not found"));
        ticket.setTitle(ticket1.getTitle());
        ticket.setDescription(ticket1.getDescription());
        ticket.setSeverity(ticket1.getSeverity());
        ticket.setStatus(ticket1.getStatus());

        final Ticket updatedTicket = ticketRepository.save(ticket);

        return updatedTicket;

    }


}