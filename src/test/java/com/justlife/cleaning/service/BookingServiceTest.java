package com.justlife.cleaning.service;

import com.justlife.cleaning.common.exception.CleaningAppBusinessException;
import com.justlife.cleaning.common.exception.CleaningAppDomainNotFoundException;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.model.Vehicle;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import com.justlife.cleaning.repository.BookingRepository;
import com.justlife.cleaning.utils.BookingTestBuilder;
import com.justlife.cleaning.utils.CheckTimeSlotFilterRequestTestBuilder;
import com.justlife.cleaning.utils.StaffTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private StaffService staffService;

    @Test
    public void it_should_find_by_booking_date() {
        // given
        LocalDate bookingDate = LocalDate.of(2022, 10, 30);
        Integer page = 1;
        Integer size = 10;

        List<Booking> bookingList = Collections.singletonList(BookingTestBuilder.buildBooking(1L));
        Page<Booking> pagedResponse = new PageImpl(bookingList);

        when(bookingRepository.findByBookingDateAndCustomerIsNull(bookingDate, PageRequest.of(page, size, Sort.by("id")))).thenReturn(pagedResponse);

        // when
        Page<Booking> bookingPage = bookingService.findByBookingDate(bookingDate, page, size);

        // then
        verify(bookingRepository).findByBookingDateAndCustomerIsNull(bookingDate, PageRequest.of(page, size, Sort.by("id")));
        assertThat(bookingPage.getContent().size()).isEqualTo(1);
    }

    @Test
    public void it_should_find_by_given_time_slot() {
        // given
        LocalDate bookingDate = LocalDate.of(2022, 10, 30);
        Integer page = 1;
        Integer size = 10;

        CheckTimeSlotFilterRequest request = CheckTimeSlotFilterRequestTestBuilder.build(bookingDate, page, size);
        LocalTime endTime = request.getStartTime().plusHours(request.getDuration()).plusMinutes(30);

        List<Booking> bookingList = Collections.singletonList(BookingTestBuilder.buildBooking(1L));
        Page<Booking> pagedResponse = new PageImpl(bookingList);

        when(bookingRepository.findByBookingDateAndStartTimeBetweenAndCustomerIsNull(request.getBookingDate(), request.getStartTime(), endTime, PageRequest.of(request.getPage(), request.getSize(), Sort.by("id")))).thenReturn(pagedResponse);

        // when
        Page<Booking> bookingPage = bookingService.findByGivenTimeSlot(request);

        // then
        verify(bookingRepository).findByBookingDateAndStartTimeBetweenAndCustomerIsNull(request.getBookingDate(), request.getStartTime(), endTime, PageRequest.of(request.getPage(), request.getSize(), Sort.by("id")));
        assertThat(bookingPage.getContent().size()).isEqualTo(1);
    }

    @Test
    public void it_should_save_all() {
        // given
        List<Booking> bookingList = Collections.singletonList(BookingTestBuilder.buildBooking(1L));

        // when
        bookingService.saveAll(bookingList);

        //then
        verify(bookingRepository).saveAll(bookingList);
    }

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
        bookingService.addBooking(booking, staffIdList, customerId);

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
        Throwable throwable = catchThrowable(() -> bookingService.addBooking(booking, staffIdList, customerId));

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
        Throwable throwable = catchThrowable(() -> bookingService.addBooking(booking, staffIdList, customerId));

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
        Throwable throwable = catchThrowable(() -> bookingService.addBooking(booking, staffIdList, customerId));

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
        Throwable throwable = catchThrowable(() -> bookingService.addBooking(booking, staffIdList, customerId));

        //then
        assertThat(throwable.getClass()).isEqualTo(CleaningAppBusinessException.class);
    }

    @Test
    public void it_should_update_booking() {
        // given
        Booking oldBooking = BookingTestBuilder.buildBooking(1L);
        oldBooking.setStartTime(LocalTime.of(10, 0));
        oldBooking.setEndTime(LocalTime.of(12, 0));
        Booking booking1 = BookingTestBuilder.buildBooking(1L);
        Booking booking2 = BookingTestBuilder.buildBooking(2L);
        booking2.setStartTime(LocalTime.of(11, 0));
        booking2.setEndTime(LocalTime.of(12, 0));
        List<Booking> oldBookingList = Arrays.asList(booking1, booking2);

        Booking updatedBooking = BookingTestBuilder.buildBooking(1L);
        updatedBooking.setStartTime(LocalTime.of(14, 0));
        updatedBooking.setEndTime(LocalTime.of(16, 0));
        Booking booking3 = BookingTestBuilder.buildBooking(1L);
        Booking booking4 = BookingTestBuilder.buildBooking(2L);
        booking3.setStartTime(LocalTime.of(14, 0));
        booking3.setEndTime(LocalTime.of(15, 0));
        booking4.setStartTime(LocalTime.of(14, 0));
        booking4.setEndTime(LocalTime.of(15, 0));
        List<Booking> updatedBookingList = Arrays.asList(booking3, booking4);

        List<Long> staffIdList = Arrays.asList(1L);
        Long customerId = 123L;

        Staff staff1 = StaffTestBuilder.buildStaff(1L);
        Staff staff2 = StaffTestBuilder.buildStaff(2L);
        List<Staff> staffList = Arrays.asList(staff1, staff2);

        when(bookingRepository.findByBookingDateAndStaffIdInAndCustomerAndStartTimeBetween(
                oldBooking.getBookingDate(), staffIdList, customerId, oldBooking.getStartTime(), oldBooking.getEndTime()))
                .thenReturn(oldBookingList);
        when(staffService.findByIdList(staffIdList)).thenReturn(staffList);
        when(bookingRepository.findByBookingDateAndStaffInAndStartTimeBetweenAndCustomerIsNull(
                oldBooking.getBookingDate(), staffIdList, updatedBooking.getStartTime(), updatedBooking.getEndTime())).thenReturn(updatedBookingList);

        // when
        bookingService.updateBooking(oldBooking, updatedBooking, staffIdList, customerId);

        //then
        verify(bookingRepository).saveAll(oldBookingList);
        verify(bookingRepository).saveAll(updatedBookingList);
    }

    @Test
    public void it_should_not_update_booking_booking_list_is_empty() {
        // given
        Booking booking = BookingTestBuilder.buildBooking(1L);
        booking.setStartTime(LocalTime.of(14, 0));
        booking.setEndTime(LocalTime.of(16, 0));
        Long customerId = 123L;
        List<Long> staffIdList = Arrays.asList(1L, 1L);

        when(bookingRepository.findByBookingDateAndStaffIdInAndCustomerAndStartTimeBetween(
                booking.getBookingDate(), staffIdList, customerId, booking.getStartTime(), booking.getEndTime())).thenReturn(Collections.emptyList());

        // when
        Throwable throwable = catchThrowable(() -> bookingService.updateBooking(booking, booking, staffIdList, customerId));

        //then
        assertThat(throwable.getClass()).isEqualTo(CleaningAppDomainNotFoundException.class);
    }

}
