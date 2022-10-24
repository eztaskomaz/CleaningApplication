package com.justlife.cleaning.model.pojo;

import com.justlife.cleaning.model.dto.CheckTimeSlotDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class CheckTimeSlotFilterRequest {

    private LocalDate bookingDate;

    private LocalTime startTime;

    private Integer duration;

    private Integer page;

    private Integer size;

    public CheckTimeSlotFilterRequest() {
    }

    public static CheckTimeSlotFilterRequest from(CheckTimeSlotDTO checkTimeSlotDTO) {
        CheckTimeSlotFilterRequest checkTimeSlotFilterRequest = new CheckTimeSlotFilterRequest();
        checkTimeSlotFilterRequest.setBookingDate(checkTimeSlotDTO.getBookingDate());
        checkTimeSlotFilterRequest.setStartTime(LocalTime.of(checkTimeSlotDTO.getStartTime(), 0));
        checkTimeSlotFilterRequest.setDuration(checkTimeSlotDTO.getDuration());
        checkTimeSlotFilterRequest.setSize(checkTimeSlotDTO.getSize());
        checkTimeSlotFilterRequest.setPage(checkTimeSlotDTO.getPage());
        return checkTimeSlotFilterRequest;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
