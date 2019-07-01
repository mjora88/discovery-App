package com.khoza.atm.service;

import com.khoza.atm.model.Customer;
import com.khoza.atm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService
{
  @Autowired
  private CustomerRepository customerRepository;

  public List<Customer> getAllClients()
  {
    return (List<Customer>) customerRepository.findAll();
  }

  public Customer getCustomerById(long customerID)
  {
    return customerRepository.findOne(customerID);
  }
}
