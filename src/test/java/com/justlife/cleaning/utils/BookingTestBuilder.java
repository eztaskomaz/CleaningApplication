package com.justlife.cleaning.utils;

import com.justlife.cleaning.model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingTestBuilder {

    public static Booking buildBooking(Long id) {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setBookingDate(LocalDate.of(2022, 10, 30));
        booking.setStartTime(LocalTime.of(10, 0));
        booking.setEndTime(LocalTime.of(12, 0));
        booking.setCustomer(CustomerTestBuilder.buildCustomer(1L));
        booking.setStaff(StaffTestBuilder.buildStaff(1L));
        return booking;
    }
}
