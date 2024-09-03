package com.example.project3.Controller;

import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // Get all Employees
    @GetMapping("/get")
    public ResponseEntity getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // Register a new Employee
    @PostMapping("/register")
    public ResponseEntity registerEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee registered successfully");
    }

    // Update an existing Employee
    @PutMapping("/update")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User user, @Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(user.getId(), employeeDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Employee updated successfully");
    }

    // Delete an Employee
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal User user,@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body("Employee deleted successfully");
    }
}
