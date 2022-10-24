package com.justlife.cleaning.service;

import com.justlife.cleaning.common.exception.CleaningAppDomainNotFoundException;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.Customer;
import com.justlife.cleaning.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingUpdateService {

    private final BookingRepository bookingRepository;
    private final BookingAddService bookingAddService;

    public BookingUpdateService(BookingRepository bookingRepository, BookingAddService bookingAddService) {
        this.bookingRepository = bookingRepository;
        this.bookingAddService = bookingAddService;
    }

    public void update(Booking oldBooking, Booking newBooking, List<Long> staffIdList, Long customerId) {
        List<Booking> oldBookingList = bookingRepository.findByBookingDateAndStaffIdInAndCustomerAndStartTimeBetween(
                oldBooking.getBookingDate(), staffIdList, customerId, oldBooking.getStartTime(), oldBooking.getEndTime());
        checkOldBookingListExists(oldBooking, staffIdList, oldBookingList);
        manageAndSaveCustomerOfBooking(oldBookingList, null);
        bookingAddService.add(newBooking, staffIdList, customerId);
    }

    private void checkOldBookingListExists(Booking oldBooking, List<Long> staffIdList, List<Booking> oldBookingList) {
        Integer oldDurationSlot = oldBooking.getEndTime().minusHours(oldBooking.getStartTime().getHour()).getHour() + 1;
        if (oldBookingList.isEmpty() || oldBookingList.size() != (staffIdList.size() * oldDurationSlot)) {
            throw new CleaningAppDomainNotFoundException("booking.is.not.found", oldBooking.getBookingDate().toString(), oldBooking.getStartTime().toString(), oldBooking.getCustomer().getId().toString());
        }
    }

    private void manageAndSaveCustomerOfBooking(List<Booking> bookingList, Customer customer) {
        bookingList.forEach(bookingDB -> bookingDB.setCustomer(customer));
        bookingRepository.saveAll(bookingList);
    }

}
