package com.justlife.cleaning.model.dto;

import com.justlife.cleaning.common.validation.DurationValid;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class UpdateBookingDTO {

    @NotNull(message = "customer.id.cannot.be.null")
    private Long customerId;

    @NotNull(message = "booking.date.cannot.be.empty")
    private LocalDate bookingDate;

    @NotNull(message = "start.time.cannot.be.empty")
    @Min(value = 8, message = "start.time.cannot.be.smaller")
    @Max(value = 20, message = "start.time.cannot.be.bigger")
    private Integer startTime;

    @DurationValid
    @NotNull(message = "duration.cannot.be.empty")
    private Integer duration;

    public UpdateBookingDTO() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
