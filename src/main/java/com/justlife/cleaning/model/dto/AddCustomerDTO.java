package com.justlife.cleaning.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AddCustomerDTO {

    @NotEmpty(message = "customer.name.cannot.be.empty")
    @Size(min = 2, max = 32, message = "customer.name.size.exceeds")
    private String name;

    @NotEmpty(message = "customer.surname.cannot.be.empty")
    @Size(min = 2, max = 32, message = "customer.surname.size.exceeds")
    private String surname;

    @NotEmpty(message = "customer.email.cannot.be.empty")
    @Email
    private String email;

    @NotEmpty(message = "customer.address.cannot.be.empty")
    @Size(max = 256, message = "customer.address.size.exceeds")
    private String address;

    public AddCustomerDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}