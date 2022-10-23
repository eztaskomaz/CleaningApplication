package com.justlife.cleaning.controller;

import com.justlife.cleaning.model.builder.CustomerBuilder;
import com.justlife.cleaning.model.dto.AddCustomerDTO;
import com.justlife.cleaning.model.dto.CustomerDTO;
import com.justlife.cleaning.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/list")
    public List<CustomerDTO> list() {
        return customerService.list();
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Valid AddCustomerDTO addCustomerDTO) {
        customerService.add(CustomerBuilder.buildCustomerWithAddCustomerDTO(addCustomerDTO));
    }

}
