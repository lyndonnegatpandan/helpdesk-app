package com.example.helpdeskspring.Service;

import com.example.helpdeskspring.Model.*;
import com.example.helpdeskspring.Repository.EmployeeRepository;
import com.example.helpdeskspring.Repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    private TicketService ticketServiceTest;

    @BeforeEach
    void setUp(){
        ticketServiceTest = new TicketService(ticketRepository, employeeRepository);
    }


    @Test
    void getAllTicket() {
        ticketServiceTest.getAllTicket();
        verify(ticketRepository).findAll();
    }

    @Test
    void getTicketById() {
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        when(ticketRepository.findById(ticket.getTicket())).thenReturn(Optional.of(ticket));
        ticketServiceTest.getTicketById(ticket.getTicket());
        verify(ticketRepository).findById(ticket.getTicket());
    }

    @Test
    void createTicket() {
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        ticketServiceTest.createTicket(ticket);
        ArgumentCaptor<Ticket> ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository).save(ticketArgumentCaptor.capture());
        Ticket capturedTicket = ticketArgumentCaptor.getValue();
        assertThat(capturedTicket).isEqualTo(ticket);
    }

    @Test
    void addAssignee() throws Exception {
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        when(ticketRepository.findById(ticket.getTicket())).thenReturn(Optional.of(ticket));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        ticketServiceTest.addAssignee(ticket.getTicket(), employee.getId());
        verify(ticketRepository).save(ticket);
        assertEquals(employee.getEmployeeNumber(), ticket.getAssignee().getEmployeeNumber());

    }

    @Test
    void addWatcher() {
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        when(ticketRepository.findById(ticket.getTicket())).thenReturn(Optional.of(ticket));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        ticketServiceTest.addWatcher(ticket.getTicket(), employee.getId());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void deleteTicket() throws Exception{
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        ticketServiceTest.deleteTicket(ticket.getTicket());
        verify(ticketRepository).deleteById(ticket.getTicket());
    }

    @Test
    void updateTicket() throws Exception{
        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        when(ticketRepository.findById(ticket.getTicket())).thenReturn(Optional.of(ticket));
        ticketServiceTest.updateTicket(ticket.getTicket(), ticket);
        verify(ticketRepository).save(ticket);
    }
}