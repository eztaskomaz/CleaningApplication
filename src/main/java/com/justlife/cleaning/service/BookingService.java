package com.justlife.cleaning.service;

import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import com.justlife.cleaning.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final BookingAddService bookingAddService;
    private final BookingUpdateService bookingUpdateService;

    public BookingService(BookingRepository bookingRepository,
                          BookingAddService bookingAddService,
                          BookingUpdateService bookingUpdateService) {
        this.bookingRepository = bookingRepository;
        this.bookingAddService = bookingAddService;
        this.bookingUpdateService = bookingUpdateService;
    }

    @Transactional(readOnly = true)
    public Page<Booking> findByBookingDate(LocalDate bookingDate, Integer page, Integer size) {
        return bookingRepository.findByBookingDateAndCustomerIsNull(bookingDate, PageRequest.of(page, size, Sort.by("id")));
    }

    @Transactional(readOnly = true)
    public Page<Booking> findByGivenTimeSlot(CheckTimeSlotFilterRequest request) {
        LocalTime endTime = request.getStartTime().plusHours(request.getDuration()).plusMinutes(30);
        return bookingRepository.findByBookingDateAndStartTimeBetweenAndCustomerIsNull(
                request.getBookingDate(), request.getStartTime(), endTime, PageRequest.of(request.getPage(), request.getSize(), Sort.by("id")));
    }

    @Transactional
    public void saveAll(List<Booking> bookingList) {
        bookingRepository.saveAll(bookingList);
        LOGGER.info("Bookings are saved. BookingIdList: " + bookingList.stream().map(Booking::getId).collect(Collectors.toList()));
    }

    @Transactional
    public void addBooking(Booking booking, List<Long> staffIdList, Long customerId) {
        LOGGER.info("Bookings are creating. Booking info: " + booking + " customerId: " + customerId + " staffIdList: " + staffIdList);
        bookingAddService.add(booking, staffIdList, customerId);
        LOGGER.info("Bookings are created. Booking info: " + booking + " customerId: " + customerId + " staffIdList: " + staffIdList);
    }

    @Transactional
    public void updateBooking(Booking oldBooking, Booking newBooking, List<Long> staffIdList, Long customerId) {
        LOGGER.info("Bookings are updating. Booking info: " + oldBooking + " customerId: " + customerId);
        bookingUpdateService.update(oldBooking, newBooking, staffIdList, customerId);
        LOGGER.info("Bookings are updated. Booking info: " + oldBooking + " customerId: " + customerId);
    }

}
