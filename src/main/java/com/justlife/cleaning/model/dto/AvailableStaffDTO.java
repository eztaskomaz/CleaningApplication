package com.justlife.cleaning.model.dto;

import com.justlife.cleaning.model.Staff;

public class AvailableStaffDTO {

    private String name;
    private String surname;
    private Integer age;
    private String gender;
    private String vehicleLicencePlate;

    public AvailableStaffDTO() {
    }

    public static AvailableStaffDTO from(Staff staff) {
        AvailableStaffDTO availableStaffDTO = new AvailableStaffDTO();
        availableStaffDTO.setName(staff.getName());
        availableStaffDTO.setSurname(staff.getSurname());
        availableStaffDTO.setAge(staff.getAge());
        availableStaffDTO.setGender(staff.getGender().name());
        availableStaffDTO.setVehicleLicencePlate(staff.getVehicle().getLicencePlate());
        return availableStaffDTO;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVehicleLicencePlate() {
        return vehicleLicencePlate;
    }

    public void setVehicleLicencePlate(String vehicleLicencePlate) {
        this.vehicleLicencePlate = vehicleLicencePlate;
    }
}
