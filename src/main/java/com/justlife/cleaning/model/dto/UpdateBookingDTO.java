package com.justlife.cleaning.model.dto;

import com.justlife.cleaning.common.validation.DurationValid;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class UpdateBookingDTO {

    @NotNull(message = "customer.id.cannot.be.null")
    private Long customerId;

    @NotNull(message = "booking.date.cannot.be.empty")
    private LocalDate bookingDate;

    @NotNull(message = "start.time.cannot.be.empty")
    @Min(value = 8, message = "start.time.cannot.be.smaller")
    @Max(value = 20, message = "start.time.cannot.be.bigger")
    private Integer oldStartTime;

    @DurationValid
    @NotNull(message = "duration.cannot.be.empty")
    private Integer oldDuration;

    @NotNull(message = "start.time.cannot.be.empty")
    @Min(value = 8, message = "start.time.cannot.be.smaller")
    @Max(value = 20, message = "start.time.cannot.be.bigger")
    private Integer newStartTime;

    @DurationValid
    @NotNull(message = "duration.cannot.be.empty")
    private Integer newDuration;

    @Size(min = 1, max = 3, message = "staff.ids.size.exceeds")
    @NotEmpty(message = "staff.ids.cannot.be.empty")
    private List<Long> staffIdList;

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

    public Integer getNewStartTime() {
        return newStartTime;
    }

    public void setNewStartTime(Integer newStartTime) {
        this.newStartTime = newStartTime;
    }

    public Integer getNewDuration() {
        return newDuration;
    }

    public void setNewDuration(Integer newDuration) {
        this.newDuration = newDuration;
    }

    public List<Long> getStaffIdList() {
        return staffIdList;
    }

    public void setStaffIdList(List<Long> staffIdList) {
        this.staffIdList = staffIdList;
    }

    public Integer getOldStartTime() {
        return oldStartTime;
    }

    public void setOldStartTime(Integer oldStartTime) {
        this.oldStartTime = oldStartTime;
    }

    public Integer getOldDuration() {
        return oldDuration;
    }

    public void setOldDuration(Integer oldDuration) {
        this.oldDuration = oldDuration;
    }
}
