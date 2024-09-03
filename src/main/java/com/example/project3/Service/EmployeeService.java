package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.EmployeeRepository;
import com.example.project3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    // Get all Employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Register a new Employee
    public void registerEmployee(EmployeeDTO employeeDTO) {
        String hash = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        User user = new User(null, employeeDTO.getUsername(), hash, employeeDTO.getName(), employeeDTO.getEmail(), "EMPLOYEE", null, null);
        Employee employee = new Employee(null, employeeDTO.getPosition(), employeeDTO.getSalary(), user);

        // Establish the bidirectional relationship
        employee.setUser(user);
        user.setEmployee(employee);

        userRepository.save(user);
        employeeRepository.save(employee);
    }

    // Update an existing Employee
    public void updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findEmployeeById(id);
        if (existingEmployee == null) {
            throw new ApiException("Employee not found");
        }

        User existingUser = existingEmployee.getUser();
        existingUser.setUsername(employeeDTO.getUsername());
        existingUser.setPassword(new BCryptPasswordEncoder().encode(employeeDTO.getPassword()));
        existingUser.setName(employeeDTO.getName());
        existingUser.setEmail(employeeDTO.getEmail());

        // Update employee details
        existingEmployee.setPosition(employeeDTO.getPosition());
        existingEmployee.setSalary(employeeDTO.getSalary());

        // Save updated entities
        userRepository.save(existingUser);
        employeeRepository.save(existingEmployee);
    }

    // Delete an Employee
    public void deleteEmployee(Integer id) {
        Employee existingEmployee = employeeRepository.findEmployeeById(id);
        if (existingEmployee == null) {
            throw new ApiException("Employee not found");
        }

        // Delete associated user first
        userRepository.delete(existingEmployee.getUser());

        // Delete the employee
        employeeRepository.delete(existingEmployee);
    }

}
