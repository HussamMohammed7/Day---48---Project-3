package com.example.project3.Controller;


import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {


    private final CustomerService customerService;


    @GetMapping("/get")
    public ResponseEntity getCustomer(@AuthenticationPrincipal User user) {

        return ResponseEntity.status(HttpStatus.OK).body(
                customerService.getAllCustomers()
        );

    }


    @PostMapping("/register-customer")
    public ResponseEntity registerCustomer(@Valid @RequestBody CustomerDTO customer) {
        customerService.registerCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(
                "user added successfully"
        );
    }


    @PutMapping("/update")
    public ResponseEntity updateCustomer(@Valid @RequestBody CustomerDTO customer,
                                         @AuthenticationPrincipal User user) {

        customerService.updateCustomer(user.getId(), customer);
        return ResponseEntity.status(HttpStatus.OK).body(
                "user updated successfully"
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user) {

        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                "user deleted successfully"
        );

    }
    @PostMapping("/createAccount")
    public ResponseEntity createAccount(@AuthenticationPrincipal User user) {
        customerService.createAccount(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                "Account created successfully for customer with ID: " + user.getId()
        );
    }

    // New endpoint to get all accounts for a customer
    @GetMapping("/accounts")
    public ResponseEntity getAccountByCustomer(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(
                customerService.getAccountByCustomer(user.getId())
        );
    }

    // New endpoint to get a specific account for a customer
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity getAccountCustomerById(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                customerService.getAccountCustomerById(user.getId(), accountId)
        );
    }
    @PostMapping("/operations/{accountId}/deposit")
    public ResponseEntity deposit(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestParam Double amount) {
        customerService.deposit(user.getId(), accountId, amount);
        return ResponseEntity.status(HttpStatus.OK).body("Deposit successful: " + amount + " added to account " + accountId);
    }

    @PostMapping("/operations/{accountId}/withdraw")
    public ResponseEntity withdraw(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestParam Double amount) {
        customerService.withdraw(user.getId(), accountId, amount);
        return ResponseEntity.status(HttpStatus.OK).body("Withdrawal successful: " + amount + " deducted from account " + accountId);
    }

    @PutMapping("/operations/{customerId}/{accountId}/activate")
    public ResponseEntity activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        customerService.activeAccount(user.getId(), accountId);
        return ResponseEntity.status(HttpStatus.OK).body("Account " + accountId + " activated successfully for customer ID: " + user.getId());
    }
    @PutMapping("/{customerId}/{accountId}/deactivate")
    public ResponseEntity deactivateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        customerService.deActiveAccount(user.getId(), accountId);
        return ResponseEntity.status(HttpStatus.OK).body("Account " + accountId + " deactivated successfully for customer ID: " + user.getId());
    }

    // New endpoint to transfer money from one account to another
    @PostMapping("/operations/transfer/{accountId}")
    public ResponseEntity transfer(
            @AuthenticationPrincipal User user,
            @PathVariable Integer accountId,
            @RequestParam String accountNumber,
            @RequestParam Double amount) {
        customerService.Transfer(user.getId(), accountId, accountNumber, amount);
        return ResponseEntity.status(HttpStatus.OK).body("Transfer successful: " + amount + " transferred to account " + accountNumber);
    }



}



