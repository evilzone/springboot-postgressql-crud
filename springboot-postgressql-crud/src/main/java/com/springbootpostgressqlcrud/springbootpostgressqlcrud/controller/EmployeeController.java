package com.springbootpostgressqlcrud.springbootpostgressqlcrud.controller;

import com.springbootpostgressqlcrud.springbootpostgressqlcrud.exception.ResourceNotFoundException;
import com.springbootpostgressqlcrud.springbootpostgressqlcrud.model.Employee;
import com.springbootpostgressqlcrud.springbootpostgressqlcrud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    // get employees
    @GetMapping("employees")
    public List<Employee> getAllEmployee() {
        return this.employeeRepository.findAll();
    }

    // get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getAllEmployeeById(@PathVariable(value= "id") long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", employeeId));
        return ResponseEntity.ok().body(employee);
    }


    // save employee
    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee);
    }

    // update employee
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") Long employeeId,
                                                   @RequestBody Employee employeeDetails)
        throws ResourceNotFoundException {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", employeeId));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        this.employeeRepository.save(employee);

        return ResponseEntity.ok().body(employee);
    }

    // delete employee
    @DeleteMapping("employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", employeeId));

        this.employeeRepository.delete(employee);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
