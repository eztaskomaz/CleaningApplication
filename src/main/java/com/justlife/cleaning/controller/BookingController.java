package com.justlife.cleaning.controller;

import com.justlife.cleaning.model.builder.BookingBuilder;
import com.justlife.cleaning.model.dto.AddBookingDTO;
import com.justlife.cleaning.model.dto.UpdateBookingDTO;
import com.justlife.cleaning.service.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/addBooking")
    public void addBooking(@RequestBody @Valid AddBookingDTO addBookingDTO) {
        bookingService.addBooking(BookingBuilder.buildByAddBookingDTO(addBookingDTO), addBookingDTO.getStaffIdList(), addBookingDTO.getCustomerId());
    }

    @PostMapping("/updateBooking")
    public void updateBooking(@RequestBody @Valid UpdateBookingDTO updateBookingDTO) {
        bookingService.updateBooking(BookingBuilder.buildByUpdateBookingDTO(updateBookingDTO), updateBookingDTO.getCustomerId());
    }

}
