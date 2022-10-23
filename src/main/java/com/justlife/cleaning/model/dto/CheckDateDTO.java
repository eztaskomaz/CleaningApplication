package com.justlife.cleaning.model.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CheckDateDTO {

    @NotNull(message = "booking.date.cannot.be.empty")
    private LocalDate bookingDate;

    @NotNull(message = "page.cannot.be.empty")
    private Integer page;

    @NotNull(message = "size.cannot.be.empty")
    private Integer size;

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
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
