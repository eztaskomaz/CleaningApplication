package com.justlife.cleaning.service;

import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.model.builder.BookingBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingScheduleService {

    private final Logger LOGGER = LoggerFactory.getLogger(BookingScheduleService.class);
    private final StaffService staffService;
    private final BookingService bookingService;

    public BookingScheduleService(StaffService staffService,
                                  BookingService bookingService) {
        this.staffService = staffService;
        this.bookingService = bookingService;
    }

    @Scheduled(cron = "0 0 * * 0")
    @Transactional
    public void createBookingScheduleEverySundayForTheNextWeek() {
        LocalDate bookingDate = LocalDate.now();
        LOGGER.info("Booking schedule is creating for week. Day: " + bookingDate);
        List<Booking> bookingList = new ArrayList<>();
        List<Staff> allStaffList = staffService.findAll();
        createBookingScheduleForEveryStaff(bookingList, allStaffList, bookingDate);
        bookingService.saveAll(bookingList);
        LOGGER.info("Booking schedule is created for the week. Day: " + bookingDate);
    }

    private void createBookingScheduleForEveryStaff(List<Booking> bookingList, List<Staff> allStaffList, LocalDate bookingDate) {
        for (Staff staff : allStaffList) {
            for (int day = 1; day < 8; day++) {
                if (day != 5) {
                    for (int hour = 8; hour < 22; hour++) {
                        createBookingSlot(bookingList, bookingDate, staff, day, hour);
                    }
                }
            }
        }
    }

    private void createBookingSlot(List<Booking> bookingList, LocalDate bookingDate, Staff staff, int day, int hour) {
        Booking booking = BookingBuilder.buildBookingSlot(bookingDate.plusDays(day), hour, staff);
        bookingList.add(booking);
    }

}
