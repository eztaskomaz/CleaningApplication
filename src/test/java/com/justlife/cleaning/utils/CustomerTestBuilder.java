package com.justlife.cleaning.utils;

import com.justlife.cleaning.model.Customer;

public class CustomerTestBuilder {

    public static Customer buildCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("name");
        customer.setSurname("surname");
        customer.setAddress("address");
        customer.setEmail("a@a.com");
        return customer;
    }

}
