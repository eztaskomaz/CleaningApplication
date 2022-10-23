package com.justlife.cleaning.model.dto;

import com.justlife.cleaning.common.validation.DurationValid;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class AddBookingDTO {

    @NotNull(message = "customer.id.cannot.be.null")
    private Long customerId;

    @Size(min = 1, max = 3, message = "staff.ids.size.exceeds")
    @NotEmpty(message = "staff.ids.cannot.be.empty")
    private List<Long> staffIdList;

    @NotNull(message = "booking.date.cannot.be.empty")
    private LocalDate bookingDate;

    @NotNull(message = "start.time.cannot.be.empty")
    @Min(value = 8, message = "start.time.cannot.be.smaller")
    @Max(value = 20, message = "start.time.cannot.be.bigger")
    private Integer startTime;

    @DurationValid
    @NotNull(message = "duration.cannot.be.empty")
    private Integer duration;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getStaffIdList() {
        return staffIdList;
    }

    public void setStaffIdList(List<Long> staffIdList) {
        this.staffIdList = staffIdList;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}