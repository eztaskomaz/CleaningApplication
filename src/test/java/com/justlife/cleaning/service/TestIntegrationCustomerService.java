package com.justlife.cleaning.service;

import com.justlife.cleaning.common.AbstractIntegrationTest;
import com.justlife.cleaning.model.Customer;
import com.justlife.cleaning.model.dto.CustomerDTO;
import com.justlife.cleaning.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestIntegrationCustomerService extends AbstractIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    public void test_list_all_customers() {
        List<CustomerDTO> list = customerService.list();

        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    @Transactional
    public void test_add_customer() {
        Customer customer = new Customer();
        customer.setName("nametest");
        customer.setSurname("surnametest");
        customer.setAddress("addresstest");
        customer.setEmail("test@mail.com");

        customerService.add(customer);

        Optional<Customer> customerByEmail = customerRepository.findByEmail(customer.getEmail());
        assertThat(customerByEmail.get().getName()).isEqualTo("nametest");
        assertThat(customerByEmail.get().getSurname()).isEqualTo("surnametest");
    }
}
