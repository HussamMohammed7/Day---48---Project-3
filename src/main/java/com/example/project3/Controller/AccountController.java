package com.example.project3.Controller;

import com.example.project3.Model.Account;
import com.example.project3.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Get all Accounts
    @GetMapping("/get")
    public ResponseEntity getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
    }

    // Create a new Account for a given customer
    @PostMapping("/create/{customerId}")
    public ResponseEntity createAccount(@PathVariable Integer customerId) {
        accountService.createAccount(customerId);
        return ResponseEntity.status(HttpStatus.OK).body("Account created successfully for customer ID: " + customerId);
    }

    // Update an existing Account
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Integer id, @RequestBody Account account) {
        accountService.updateAccount(id, account);
        return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully");
    }

    // Delete an Account
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.OK).body("Account deleted successfully");
    }



}
