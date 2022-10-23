package com.justlife.cleaning.model.dto;

import com.justlife.cleaning.model.Booking;

import java.time.LocalTime;

public class AvailableStaffTimeDTO {

    private AvailableStaffDTO staff;
    private LocalTime availableTime;

    public AvailableStaffTimeDTO() {
    }

    public static AvailableStaffTimeDTO from(Booking booking) {
        AvailableStaffTimeDTO availableStaffTimeDTO = new AvailableStaffTimeDTO();
        availableStaffTimeDTO.setStaff(AvailableStaffDTO.from(booking.getStaff()));
        availableStaffTimeDTO.setAvailableTime(booking.getStartTime());
        return availableStaffTimeDTO;
    }

    public AvailableStaffDTO getStaff() {
        return staff;
    }

    public void setStaff(AvailableStaffDTO staff) {
        this.staff = staff;
    }

    public LocalTime getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(LocalTime availableTime) {
        this.availableTime = availableTime;
    }
}
