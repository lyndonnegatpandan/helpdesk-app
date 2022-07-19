package com.example.helpdeskspring.Service;

import com.example.helpdeskspring.Model.*;
import com.example.helpdeskspring.Repository.EmployeeRepository;
import com.example.helpdeskspring.Repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TicketRepository ticketRepository;
    private EmployeeService employeeServiceTest;

    @BeforeEach
    void setUp(){
        employeeServiceTest = new EmployeeService(employeeRepository);
    }

    @Test
    void canGetEmployees() {
        //when
        employeeServiceTest.getEmployees();
        //then
        verify(employeeRepository).findAll();
    }


    @Test
    void canCreateEmployee() {
        //given
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        //when
        employeeServiceTest.createEmployee(employee);
        //then
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeRepository).save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();

        assertThat(capturedEmployee).isEqualTo(employee);
    }

    @Test
    void getEmployeeById(){
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        employeeServiceTest.getEmployeeById(employee.getId());
        verify(employeeRepository).findById(employee.getId());

    }

    @Test
    void updateEmployee() throws Exception {
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        employeeServiceTest.updateEmployee(employee.getId(), employee);
        verify(employeeRepository).save(employee);


    }

    @Test
    void deleteEmployee() throws Exception {
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        Boolean test = employeeServiceTest.deleteEmployee(employee.getId());
        verify(employeeRepository).deleteById(employee.getId());
        assertTrue(test);

    }

    @Test
    void deleteEmployeeException(){
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);

        Ticket ticket = new Ticket("First Ticket", "This is the first ticket.", Severity.Major, Status.New);
        Set<Ticket> ticketAssigned = new HashSet<>();
        ticketAssigned.add(ticket);
        employee.setTicketAssigned(ticketAssigned);
        given(employeeRepository.findById(any())).willReturn(Optional.of(employee));
        assertThatThrownBy(()-> employeeServiceTest.deleteEmployee(employee.getId()))
                .hasMessage("Employee currently assigned.");
        verify(employeeRepository, never()).delete(any());

    }

}