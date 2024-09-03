package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    // Get all Accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Create a new Account for a given customer
    public void createAccount(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        String accountNumber = generateAccountNumber();
        Account account = new Account(null, accountNumber, 0.0, false, customer);
        accountRepository.save(account);
    }

    // Update an existing Account
    public void updateAccount(Integer id, Account account) {
        Account existingAccount = accountRepository.findAccountById(id);
        if (existingAccount == null) {
            throw new ApiException("Account not found");
        }

        existingAccount.setAccountNumber(account.getAccountNumber());
        existingAccount.setBalance(account.getBalance());
        existingAccount.setIsActive(account.getIsActive());
        accountRepository.save(existingAccount);
    }

    // Delete an Account
    public void deleteAccount(Integer id) {
        Account account = accountRepository.findAccountById(id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        accountRepository.delete(account);
    }

    public String generateAccountNumber() {

        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            if (i > 0) accountNumber.append("-");
            accountNumber.append(String.format("%04d", random.nextInt(10000)));
        }
        return accountNumber.toString();
    }

}
