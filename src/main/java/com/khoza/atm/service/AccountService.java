package com.khoza.atm.service;

import com.khoza.atm.model.Account;
import com.khoza.atm.model.Customer;
import com.khoza.atm.repository.AccountRepository;
import com.khoza.atm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService
{
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AccountRepository accountRepository;

  public List<Customer> getAllClients()
  {
    return (List<Customer>) customerRepository.findAll();
  }

  public Account findById(long id)
  {
    return accountRepository.findOne(id);
  }
}
