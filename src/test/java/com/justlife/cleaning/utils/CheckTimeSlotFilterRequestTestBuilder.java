package com.justlife.cleaning.utils;

import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public class CheckTimeSlotFilterRequestTestBuilder {

    public static CheckTimeSlotFilterRequest build(LocalDate bookingDate, Integer page, Integer size) {
        CheckTimeSlotFilterRequest request = new CheckTimeSlotFilterRequest();
        request.setBookingDate(bookingDate);
        request.setStartTime(LocalTime.of(10, 0));
        request.setDuration(2);
        request.setPage(page);
        request.setSize(size);
        return request;
    }
}
