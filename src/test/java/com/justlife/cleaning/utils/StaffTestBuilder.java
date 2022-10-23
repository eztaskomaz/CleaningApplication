package com.justlife.cleaning.utils;

import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.model.Vehicle;

public class StaffTestBuilder {

    public static Staff buildStaff(Long id) {
        Staff staff = new Staff();
        staff.setId(id);
        staff.setName("name");
        staff.setSurname("surname");
        staff.setAge(27);
        staff.setGender(Staff.Gender.FEMALE);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setDriverName("driver");
        vehicle.setLicencePlate("06DN123");
        staff.setVehicle(vehicle);
        return staff;
    }

}
