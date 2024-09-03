package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.CustomerRepository;
import com.example.project3.Repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void registerCustomer(CustomerDTO customer) {

        String hash = new BCryptPasswordEncoder().encode(customer.getPassword());
        User user = new User(null, customer.getUsername(), hash, customer.getName(), customer.getEmail(), "CUSTOMER", null, null);
        Customer customer1 = new Customer(null, customer.getPhoneNumber(), user, null);

        customer1.setUser(user);
        user.setCustomer(customer1);

        userRepository.save(user);
        customerRepository.save(customer1);
    }

    public void updateCustomer(Integer id, CustomerDTO customerDTO) {

        Customer existingCustomer = customerRepository.findCustomerById(id);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        User existingUser = existingCustomer.getUser();
        existingUser.setUsername(customerDTO.getUsername());
        existingUser.setPassword(new BCryptPasswordEncoder().encode(customerDTO.getPassword()));
        existingUser.setName(customerDTO.getName());
        existingUser.setEmail(customerDTO.getEmail());

        // Update customer details
        existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());

        // Save updated entities
        userRepository.save(existingUser);
        customerRepository.save(existingCustomer);
    }

    // Delete a Customer
    public void deleteCustomer(Integer id) {
        // Use the repository method to find the customer by ID
        Customer existingCustomer = customerRepository.findCustomerById(id);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        // Delete associated user first
        userRepository.delete(existingCustomer.getUser());

        // Delete the customer
        customerRepository.delete(existingCustomer);
    }

    public void createAccount(Integer id) {
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        String accountNumber = accountService.generateAccountNumber();

        Account account = new Account(null, accountNumber, 0.0, false, customer);
        customer.getAccounts().add(account);
        customerRepository.save(customer);
    }

    public List<Account> getAccountByCustomer(Integer customerId) {

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        List<Account> accounts = accountRepository.findAllByCustomer(customer);
        if (accounts.isEmpty()) {
            throw new ApiException("You dont have accounts open one !!");
        }

        return accounts;


    }

    public Account getAccountCustomerById(Integer customerId, Integer accountId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        List<Account> accounts = accountRepository.findAllByCustomer(customer);
        if (accounts.isEmpty()) {
            throw new ApiException("You dont have accounts open one !!");
        }

        for (Account account : accounts) {
            if (account.getId() == accountId) {
                return account;
            }
        }

        throw new ApiException("Account not found");
    }

    public void deposit(Integer customerId, Integer accountId, Double amount) {
        if (amount <= 0) {
            throw new ApiException("Deposit amount must be greater than zero");
        }

        Account account = getAccountCustomerById(customerId, accountId);

        if (!account.getIsActive()) {
            throw new ApiException("Account not active");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }
    public void withdraw(Integer customerId, Integer accountId, Double amount) {
        if (amount <= 0) {
            throw new ApiException("Withdraw amount must be greater than zero");
        }

        Account account = getAccountCustomerById(customerId, accountId);

        if (!account.getIsActive()) {
            throw new ApiException("Account not active");
        }

        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void activeAccount(Integer customerId, Integer accountId) {
        Account account = getAccountCustomerById(customerId, accountId);
        account.setIsActive(true);
        accountRepository.save(account);
    }
    public void deActiveAccount(Integer customerId, Integer accountId) {
        Account account = getAccountCustomerById(customerId, accountId);
        account.setIsActive(false);
        accountRepository.save(account);
    }


    public void Transfer(Integer customerId, Integer accountId , String accountNumber, Double amount) {

        Account account = getAccountCustomerById(customerId, accountId);
        if (!account.getIsActive()) {
            throw new ApiException("Account not active");
        }
        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient funds");
        }
        Account accountReceiver = accountRepository.findByAccountNumber(accountNumber);
        if (accountReceiver == null) {
            throw new ApiException("Account not found");
        }
        account.setBalance(account.getBalance() - amount);
        accountReceiver.setBalance(accountReceiver.getBalance() + amount);
        accountRepository.save(account);
        accountRepository.save(accountReceiver);

    }


}
