package com.example.project3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @NotNull(message = "Username cannot be null")
    @Size(min = 4, max = 10, message = "Username length must be between 4 and 10 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password length must be at least 6 characters")
    private String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name length must be between 2 and 20 characters")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be in a valid format")
    private String email;


    @NotNull(message = "Position cannot be null")
    private String position;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero( message = "Salary must be a non-negative decimal number")
    private Double salary;

}
