package com.example.helpdeskspring.Service;

import com.example.helpdeskspring.Model.Employee;
import com.example.helpdeskspring.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(long id){
        return employeeRepository.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(long id, Employee employee1)throws Exception{

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new Exception("Employee not found"));
        employee.setEmployeeNumber(employee1.getEmployeeNumber());
        employee.setFirstName(employee1.getFirstName());
        employee.setMiddleName(employee1.getMiddleName());
        employee.setLastName(employee1.getLastName());
        employee.setDepartment(employee1.getDepartment());

        final Employee updatedEmployee = employeeRepository.save(employee);

        return updatedEmployee;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteEmployee(long id)throws Exception{
        Employee employee = employeeRepository.findById(id).get();
        if(employee.getTicketAssigned()==null){
            employeeRepository.deleteById(id);

        }else{
            throw new Exception("Employee currently assigned.");
        }
        return true;

    }

}