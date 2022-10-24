package com.justlife.cleaning.service;

import com.justlife.cleaning.common.exception.CleaningAppBusinessException;
import com.justlife.cleaning.common.exception.CleaningAppDomainNotFoundException;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.model.Vehicle;
import com.justlife.cleaning.repository.BookingRepository;
import com.justlife.cleaning.utils.BookingTestBuilder;
import com.justlife.cleaning.utils.StaffTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingAddServiceTest {

    @InjectMocks
    private BookingAddService bookingAddService;

    @Mock
    private StaffService staffService;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    public void it_should_add_booking() {
        // given
        Booking booking = BookingTestBuilder.buildBooking(1L);

        Booking booking1 = BookingTestBuilder.buildBooking(1L);
        Booking booking2 = BookingTestBuilder.buildBooking(2L);
        booking2.setStartTime(LocalTime.of(11, 0));
        booking2.setEndTime(LocalTime.of(12, 0));
        List<Booking> bookingList = Arrays.asList(booking1, booking2);
        List<Long> staffIdList = Arrays.asList(1L);
        Long customerId = 123L;

        Staff staff1 = StaffTestBuilder.buildStaff(1L);
        List<Staff> staffList = Arrays.asList(staff1);

        when(staffService.findByIdList(staffIdList)).thenReturn(staffList);
        when(bookingRepository.findByBookingDateAndStaffInAndStartTimeBetweenAndCustomerIsNull(
                booking.getBookingDate(), staffIdList, booking.getStartTime(), booking.getEndTime())).thenReturn(bookingList);

        // when
        bookingAddService.add(booking, staffIdList, customerId);

        //then
        verify(bookingRepository).saveAll(bookingList);
    }

    @Test
    public void it_should_not_add_booking_staff_list_is_empty() {
        // given
        Booking booking = BookingTestBuilder.buildBooking(1L);

        List<Long> staffIdList = Arrays.asList(1L, 2L);
        Long customerId = 123L;

        when(staffService.findByIdList(staffIdList)).thenReturn(Collections.emptyList());

        // when
        Throwable throwable = catchThrowable(() -> bookingAddService.add(booking, staffIdList, customerId));

        //then
        verifyNoInteractions(bookingRepository);
        assertThat(throwable.getClass()).isEqualTo(CleaningAppDomainNotFoundException.class);
    }

    @Test
    public void it_should_not_add_booking_vehicles_are_not_the_same() {
        // given
        Booking booking = BookingTestBuilder.buildBooking(1L);

        List<Long> staffIdList = Arrays.asList(1L, 2L);
        Long customerId = 123L;

        Staff staff1 = StaffTestBuilder.buildStaff(1L);
        Vehicle vehicle = new Vehicle();
        vehicle.setLicencePlate("06LP111");
        staff1.setVehicle(vehicle);
        Staff staff2 = StaffTestBuilder.buildStaff(2L);
        List<Staff> staffList = Arrays.asList(staff1, staff2);

        when(staffService.findByIdList(staffIdList)).thenReturn(staffList);

        // when
        Throwable throwable = catchThrowable(() -> bookingAddService.add(booking, staffIdList, customerId));

        //then
        verifyNoInteractions(bookingRepository);
        assertThat(throwable.getClass()).isEqualTo(CleaningAppBusinessException.class);
    }

    @Test
    public void it_should_not_add_booking_available_booking_list_empty() {
        // given
        Booking booking = BookingTestBuilder.buildBooking(1L);

        List<Long> staffIdList = Arrays.asList(1L, 2L);
        Long customerId = 123L;

        Staff staff1 = StaffTestBuilder.buildStaff(1L);
        Staff staff2 = StaffTestBuilder.buildStaff(2L);
        List<Staff> staffList = Arrays.asList(staff1, staff2);

        when(staffService.findByIdList(staffIdList)).thenReturn(staffList);
        when(bookingRepository.findByBookingDateAndStaffInAndStartTimeBetweenAndCustomerIsNull(
                booking.getBookingDate(), staffIdList, booking.getStartTime(), booking.getEndTime())).thenReturn(Collections.emptyList());

        // when
        Throwable throwable = catchThrowable(() -> bookingAddService.add(booking, staffIdList, customerId));

        //then
        assertThat(throwable.getClass()).isEqualTo(CleaningAppBusinessException.class);
    }

    @Test
    public void it_should_not_add_booking_available_booking_list_size_is_different() {
        // given
        Booking booking = BookingTestBuilder.buildBooking(1L);

        List<Long> staffIdList = Arrays.asList(1L, 2L);
        Long customerId = 123L;

        Staff staff1 = StaffTestBuilder.buildStaff(1L);
        Staff staff2 = StaffTestBuilder.buildStaff(2L);
        List<Staff> staffList = Arrays.asList(staff1, staff2);
        Booking booking1 = BookingTestBuilder.buildBooking(1L);
        List<Booking> bookingList = Arrays.asList(booking1);

        when(staffService.findByIdList(staffIdList)).thenReturn(staffList);
        when(bookingRepository.findByBookingDateAndStaffInAndStartTimeBetweenAndCustomerIsNull(
                booking.getBookingDate(), staffIdList, booking.getStartTime(), booking.getEndTime())).thenReturn(bookingList);

        // when
        Throwable throwable = catchThrowable(() -> bookingAddService.add(booking, staffIdList, customerId));

        //then
        assertThat(throwable.getClass()).isEqualTo(CleaningAppBusinessException.class);
    }

}
