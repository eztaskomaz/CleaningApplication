package com.justlife.cleaning.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justlife.cleaning.model.Customer;
import com.justlife.cleaning.model.dto.AddCustomerDTO;
import com.justlife.cleaning.model.dto.CustomerDTO;
import com.justlife.cleaning.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController controller;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller).build();
    }

    @Test
    void it_should_find_available_times() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("name");
        customerDTO.setSurname("surname");
        customerDTO.setEmail("a@a.com");
        customerDTO.setAddress("address");
        List<CustomerDTO> customerDTOList = Collections.singletonList(customerDTO);

        when(customerService.list()).thenReturn(customerDTOList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/customer/list"));

        //then
        resultActions.andExpect(jsonPath("$[0].name").value("name"));
        resultActions.andExpect(jsonPath("$[0].surname").value("surname"));
        resultActions.andExpect(jsonPath("$[0].address").value("address"));
        resultActions.andExpect(jsonPath("$[0].email").value("a@a.com"));
    }

    @Test
    void it_should_add() throws Exception {
        //given
        AddCustomerDTO addCustomerDTO = new AddCustomerDTO();
        addCustomerDTO.setName("name");
        addCustomerDTO.setSurname("surname");
        addCustomerDTO.setEmail("a@a.com");
        addCustomerDTO.setAddress("address");

        String jsonBody = new ObjectMapper().writeValueAsString(addCustomerDTO);

        //when
        mockMvc.perform(post("/customer/add")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).add(customerArgumentCaptor.capture());
        Customer capturedRequest = customerArgumentCaptor.getValue();
        assertThat(capturedRequest.getName()).isEqualTo("name");
    }

}
