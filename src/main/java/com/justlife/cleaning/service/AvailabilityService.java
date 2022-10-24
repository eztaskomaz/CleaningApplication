package com.justlife.cleaning.service;

import com.justlife.cleaning.common.exception.CleaningAppBusinessException;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.model.dto.AvailableStaffDTO;
import com.justlife.cleaning.model.dto.AvailableStaffTimeDTO;
import com.justlife.cleaning.model.pageable.PageableResult;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private final BookingService bookingService;

    public AvailabilityService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Transactional(readOnly = true)
    public PageableResult<AvailableStaffTimeDTO> findAvailableTimes(LocalDate bookingDate, Integer page, Integer size) {
        Page<Booking> bookingList = bookingService.findByBookingDate(bookingDate, page, size);
        if (bookingList.isEmpty()) {
            throw new CleaningAppBusinessException("booking.is.not.available.for.booking.date", bookingDate.toString());
        }
        List<AvailableStaffTimeDTO> availableStaffTimeDTOList = bookingList.getContent().stream().map(AvailableStaffTimeDTO::from).collect(Collectors.toList());
        return new PageableResult<>(bookingList.getTotalElements(), page, size, availableStaffTimeDTOList);
    }

    @Transactional(readOnly = true)
    public PageableResult<AvailableStaffDTO> checkTimeSlotAvailable(CheckTimeSlotFilterRequest request) {
        Page<Booking> bookingList = bookingService.findByGivenTimeSlot(request);
        if (bookingList.isEmpty()) {
            throw new CleaningAppBusinessException("booking.is.not.available", request.getBookingDate().toString(), request.getStartTime().toString());
        }
        Set<Staff> staffSet = bookingList.getContent().stream().map(Booking::getStaff).collect(Collectors.toSet());
        List<AvailableStaffDTO> availableStaffDTOList = staffSet.stream().map(AvailableStaffDTO::from).collect(Collectors.toList());
        return new PageableResult<>(bookingList.getTotalElements(), request.getPage(), request.getSize(), availableStaffDTOList);
    }

}