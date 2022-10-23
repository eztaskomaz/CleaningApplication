package com.justlife.cleaning.model.builder;

import com.justlife.cleaning.model.Customer;
import com.justlife.cleaning.model.dto.AddCustomerDTO;

public class CustomerBuilder {

    public static Customer buildCustomerWithAddCustomerDTO(AddCustomerDTO addCustomerDTO) {
        Customer customer = new Customer();
        customer.setName(addCustomerDTO.getName());
        customer.setSurname(addCustomerDTO.getSurname());
        customer.setEmail(addCustomerDTO.getEmail());
        customer.setAddress(addCustomerDTO.getAddress());
        return customer;
    }
}
