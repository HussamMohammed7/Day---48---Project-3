package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Account number cannot be null")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$", message = "Account number must follow the format 'XXXX-XXXX-XXXX-XXXX'")
    @Column(columnDefinition = "VARCHAR(19) NOT NULL")
    private String accountNumber;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance must be a non-negative decimal number")
    @Column(columnDefinition = "DECIMAL(10, 2) NOT NULL")
    private Double balance;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActive = false;


    @ManyToOne
    @JsonIgnore
    private Customer customer;
}
