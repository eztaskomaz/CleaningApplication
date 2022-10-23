package com.justlife.cleaning.service;

import com.justlife.cleaning.common.exception.CleaningAppBusinessException;
import com.justlife.cleaning.common.exception.CleaningAppDomainNotFoundException;
import com.justlife.cleaning.model.*;
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
    private final StaffService staffService;

    public BookingService(BookingRepository bookingRepository, StaffService staffService) {
        this.bookingRepository = bookingRepository;
        this.staffService = staffService;
    }

    @Transactional(readOnly = true)
    public Page<Booking> findByBookingDate(LocalDate bookingDate, Integer page, Integer size) {
        return bookingRepository.findByBookingDateAndCustomerIsNull(bookingDate, PageRequest.of(page, size, Sort.by("id")));
    }

    @Transactional(readOnly = true)
    public Page<Booking> findByGivenTimeSlot(CheckTimeSlotFilterRequest request) {
        LocalTime endTime = request.getStartTime().plusHours(request.getDuration()).plusMinutes(30);
        return bookingRepository.findByBookingDateAndStartTimeBetween(
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
        checkAllStaffAreInTheSameVehicle(staffIdList);
        prepareAndSaveBookings(booking, customerId, staffIdList);
        LOGGER.info("Bookings are created. Booking info: " + booking + " customerId: " + customerId + " staffIdList: " + staffIdList);
    }

    @Transactional
    public void updateBooking(Booking booking, Long customerId) {
        LOGGER.info("Bookings are updating. Booking info: " + booking + " customerId: " + customerId);
        List<Booking> bookingList = bookingRepository.findByBookingDateAndCustomerAndStartTimeBetween(
                booking.getBookingDate(), booking.getCustomer().getId(), booking.getStartTime(), booking.getEndTime());
        if (bookingList.isEmpty()) {
            throw new CleaningAppDomainNotFoundException("booking.is.not.found", booking.getBookingDate().toString(), booking.getStartTime().toString(), booking.getCustomer().getId().toString());
        }
        manageAndSaveCustomerOfBooking(bookingList, null);
        List<Long> staffIdList = bookingList.stream().map(Booking::getStaff).map(AbstractAuditedEntity::getId).collect(Collectors.toList());
        addBooking(booking, staffIdList, customerId);
        LOGGER.info("Bookings are updated. Booking info: " + booking + " customerId: " + customerId);
    }

    private void checkAllStaffAreInTheSameVehicle(List<Long> staffIdList) {
        List<Staff> staffList = staffService.findByIdList(staffIdList);
        if(staffList.isEmpty()) {
            throw new CleaningAppDomainNotFoundException("staffs.are.not.found", staffIdList.toString());
        }
        boolean allVehiclesAreTheSame = staffList.stream().map(Staff::getVehicle).map(Vehicle::getLicencePlate).distinct().count() == 1;
        if (!allVehiclesAreTheSame) {
            throw new CleaningAppBusinessException("staffs.are.not.in.the.same.vehicle");
        }
    }

    private void prepareAndSaveBookings(Booking booking, Long customerId, List<Long> staffIdList) {
        List<Booking> bookingList = bookingRepository.findByBookingDateAndStaffInAndStartTimeBetween(
                booking.getBookingDate(), staffIdList, booking.getStartTime(), booking.getEndTime());
        checkBookingIsAvailable(booking, bookingList);
        manageAndSaveCustomerOfBooking(bookingList, Customer.from(customerId));
    }

    private void checkBookingIsAvailable(Booking booking, List<Booking> bookingList) {
        if (bookingList.isEmpty() || (bookingList.size() != (booking.getEndTime().getHour() - booking.getStartTime().getHour()))) {
            throw new CleaningAppBusinessException("booking.is.not.available", booking.getBookingDate().toString(), booking.getStartTime().toString());
        }
    }

    private void manageAndSaveCustomerOfBooking(List<Booking> bookingList, Customer customer) {
        bookingList.forEach(bookingDB -> bookingDB.setCustomer(customer));
        bookingRepository.saveAll(bookingList);
    }

}
