package com.justlife.cleaning.service;

import com.justlife.cleaning.model.Customer;
import com.justlife.cleaning.model.dto.CustomerDTO;
import com.justlife.cleaning.repository.CustomerRepository;
import com.justlife.cleaning.utils.CustomerTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void it_should_list() {
        // given
        List<Customer> customerList = Arrays.asList(CustomerTestBuilder.buildCustomer(1L));

        when(customerRepository.findAll()).thenReturn(customerList);

        // when
        List<CustomerDTO> customerDTOList = customerService.list();

        // then
        verify(customerRepository).findAll();
        assertThat(customerDTOList.get(0).getName()).isEqualTo("name");
        assertThat(customerDTOList.get(0).getSurname()).isEqualTo("surname");
        assertThat(customerDTOList.get(0).getEmail()).isEqualTo("a@a.com");
        assertThat(customerDTOList.get(0).getAddress()).isEqualTo("address");
    }

    @Test
    public void it_should_add() {
        // given
        Customer customer = CustomerTestBuilder.buildCustomer(1L);

        // when
        customerService.add(customer);

        // then
        verify(customerRepository).save(customer);
    }

}
