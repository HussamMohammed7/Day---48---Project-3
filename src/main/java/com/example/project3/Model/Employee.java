package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Position cannot be null")
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String position;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero( message = "Salary must be a non-negative decimal number")
    @Column(columnDefinition = "DECIMAL(10, 2) NOT NULL")
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user ;
}
