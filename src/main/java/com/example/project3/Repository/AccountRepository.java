package com.example.project3.Repository;

import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Integer>{

    Account findAccountById(Integer account);

    List<Account> findAllByCustomer(Customer customer);

    Account findByAccountNumber (String accountNumber);
    Account findByIdAndCustomerId(Integer accountId, Integer customerId);

}
