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

    public static Booking buildBookingForDB() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.of(2022, 10, 29));
        booking.setStartTime(LocalTime.of(10, 0));
        booking.setEndTime(LocalTime.of(11, 0));
        booking.setStaff(StaffTestBuilder.buildStaff(100001L));
        return booking;
    }

    public static Booking buildBookingForAdding() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.of(2022, 10, 30));
        booking.setStartTime(LocalTime.of(12, 0));
        booking.setEndTime(LocalTime.of(14, 0));
        booking.setCustomer(CustomerTestBuilder.buildCustomer(100001L));
        return booking;
    }

    public static Booking buildOldBookingForUpdating() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.of(2022, 10, 30));
        booking.setStartTime(LocalTime.of(8, 0));
        booking.setEndTime(LocalTime.of(10, 30));
        booking.setCustomer(CustomerTestBuilder.buildCustomer(100001L));
        return booking;
    }

    public static Booking buildNewBookingForUpdating() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.of(2022, 10, 30));
        booking.setStartTime(LocalTime.of(9, 0));
        booking.setEndTime(LocalTime.of(11, 30));
        booking.setCustomer(CustomerTestBuilder.buildCustomer(100001L));
        return booking;
    }
}
