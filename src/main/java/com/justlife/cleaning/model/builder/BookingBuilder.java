package com.justlife.cleaning.model.builder;

import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.model.dto.AddBookingDTO;
import com.justlife.cleaning.model.dto.UpdateBookingDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingBuilder {

    public static Booking buildBookingWithBookingDateAndStaff(LocalDate bookingDate, Staff staff) {
        Booking booking = new Booking();
        booking.setBookingDate(bookingDate);
        booking.setStaff(staff);
        return booking;
    }

    public static Booking buildBookingWithBookingDateAndTime(LocalDate bookingDate, Integer startTime, Integer duration) {
        Booking booking = new Booking();
        booking.setBookingDate(bookingDate);
        booking.setStartTime(LocalTime.of(startTime, 0));
        booking.setEndTime(LocalTime.of(startTime, 0).plusHours(duration).plusMinutes(30));
        return booking;
    }

    public static Booking buildBookingSlot(LocalDate bookingDate, int startTime, Staff staff) {
        Booking booking = buildBookingWithBookingDateAndStaff(bookingDate, staff);
        booking.setStartTime(LocalTime.of(startTime, 0));
        booking.setEndTime(LocalTime.of(startTime + 1, 0));
        return booking;
    }

    public static Booking buildByAddBookingDTO(AddBookingDTO addBookingDTO) {
        return buildBookingWithBookingDateAndTime(
                addBookingDTO.getBookingDate(),
                addBookingDTO.getStartTime(),
                addBookingDTO.getDuration());
    }

    public static Booking buildNewBookingByUpdateBookingDTO(UpdateBookingDTO updateBookingDTO) {
        return buildBookingWithBookingDateAndTime(
                updateBookingDTO.getBookingDate(),
                updateBookingDTO.getNewStartTime(),
                updateBookingDTO.getNewDuration());
    }

    public static Booking buildOldBookingByUpdateBookingDTO(UpdateBookingDTO updateBookingDTO) {
        return buildBookingWithBookingDateAndTime(
                updateBookingDTO.getBookingDate(),
                updateBookingDTO.getOldStartTime(),
                updateBookingDTO.getOldDuration());
    }
}
