package com.justlife.cleaning.service;

import com.justlife.cleaning.model.Customer;
import com.justlife.cleaning.model.dto.CustomerDTO;
import com.justlife.cleaning.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void add(Customer customer) {
        customerRepository.save(customer);
        LOGGER.info("Customer is saved. Customer: " + customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> list() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(CustomerDTO::from).collect(Collectors.toList());
    }

}
