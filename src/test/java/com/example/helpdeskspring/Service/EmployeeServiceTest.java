package com.example.helpdeskspring.Service;

import com.example.helpdeskspring.Model.Department;
import com.example.helpdeskspring.Model.Employee;
import com.example.helpdeskspring.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
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
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        employeeServiceTest.getEmployeeById(employee.getId());
        verify(employeeRepository).findById(employee.getId());

    }

    @Test
    void updateEmployee() throws Exception {
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        employeeServiceTest.updateEmployee(employee.getId(), employee);
        verify(employeeRepository).save(employee);


    }

    @Test
    void deleteEmployee() throws Exception {
        Employee employee = new Employee(1222, "Sarah", "Santisima", "Geronimo", Department.valueOf("IT"), null,null);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        employeeServiceTest.deleteEmployee(employee.getId());
        verify(employeeRepository).deleteById(employee.getId());

    }
}