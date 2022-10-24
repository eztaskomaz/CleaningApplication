package com.justlife.cleaning.service;

import com.justlife.cleaning.common.exception.CleaningAppBusinessException;
import com.justlife.cleaning.common.exception.CleaningAppDomainNotFoundException;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.Customer;
import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.model.Vehicle;
import com.justlife.cleaning.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingAddService {

    private final StaffService staffService;
    private final BookingRepository bookingRepository;

    public BookingAddService(StaffService staffService, BookingRepository bookingRepository) {
        this.staffService = staffService;
        this.bookingRepository = bookingRepository;
    }

    public void add(Booking booking, List<Long> staffIdList, Long customerId) {
        checkAllStaffAreInTheSameVehicle(staffIdList);
        prepareAndSaveBookings(booking, customerId, staffIdList);
    }

    private void checkAllStaffAreInTheSameVehicle(List<Long> staffIdList) {
        List<Staff> staffList = staffService.findByIdList(staffIdList);
        if (staffList.isEmpty()) {
            throw new CleaningAppDomainNotFoundException("staffs.are.not.found", staffIdList.toString());
        }
        boolean allVehiclesAreTheSame = staffList.stream().map(Staff::getVehicle).map(Vehicle::getLicencePlate).distinct().count() == 1;
        if (!allVehiclesAreTheSame) {
            throw new CleaningAppBusinessException("staffs.are.not.in.the.same.vehicle");
        }
    }

    private void prepareAndSaveBookings(Booking booking, Long customerId, List<Long> staffIdList) {
        List<Booking> bookingList = bookingRepository.findByBookingDateAndStaffInAndStartTimeBetweenAndCustomerIsNull(
                booking.getBookingDate(), staffIdList, booking.getStartTime(), booking.getEndTime());
        checkBookingIsAvailable(booking, bookingList, staffIdList.size());
        manageAndSaveCustomerOfBooking(bookingList, Customer.from(customerId));
    }

    private void checkBookingIsAvailable(Booking booking, List<Booking> bookingList, Integer staffSize) {
        if (bookingList.isEmpty() || ((bookingList.size() / staffSize) < (booking.getEndTime().getHour() - booking.getStartTime().getHour()))) {
            throw new CleaningAppBusinessException("booking.is.not.available", booking.getBookingDate().toString(), booking.getStartTime().toString());
        }
    }

    private void manageAndSaveCustomerOfBooking(List<Booking> bookingList, Customer customer) {
        bookingList.forEach(bookingDB -> bookingDB.setCustomer(customer));
        bookingRepository.saveAll(bookingList);
    }

}