package com.example.helpdeskspring.Controller;


import com.example.helpdeskspring.Model.Employee;
import com.example.helpdeskspring.Repository.EmployeeRepository;
import com.example.helpdeskspring.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository){
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }
    //
    @GetMapping("/list")
    public ResponseEntity<Employee> getEmployees(){
        return new ResponseEntity(employeeService.getEmployees(), HttpStatus.OK);
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity <Employee> addEmployee(@RequestBody Employee employee){
        return new ResponseEntity(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    Optional<Employee> getEmployeeById(@PathVariable("id")long id){
        return employeeService.getEmployeeById(id);
    }


    @PutMapping(path="/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") long id, @RequestBody Employee employee1) throws Exception {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employee1), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable(value="id")long id)throws Exception{
        employeeService.deleteEmployee(id);
    }


}