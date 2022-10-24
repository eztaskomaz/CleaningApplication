package com.justlife.cleaning.service;

import com.justlife.cleaning.common.exception.CleaningAppDomainNotFoundException;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.repository.BookingRepository;
import com.justlife.cleaning.utils.BookingTestBuilder;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingUpdateServiceTest {

    @InjectMocks
    private BookingUpdateService bookingUpdateService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingAddService bookingAddService;

    @Test
    public void it_should_update_booking() {
        // given
        Booking oldBooking = BookingTestBuilder.buildBooking(1L);
        oldBooking.setStartTime(LocalTime.of(10, 0));
        oldBooking.setEndTime(LocalTime.of(12, 0));
        Booking booking1 = BookingTestBuilder.buildBooking(1L);
        Booking booking2 = BookingTestBuilder.buildBooking(2L);
        Booking booking5 = BookingTestBuilder.buildBooking(3L);
        booking2.setStartTime(LocalTime.of(11, 0));
        booking2.setEndTime(LocalTime.of(12, 0));
        booking5.setStartTime(LocalTime.of(12, 0));
        booking5.setEndTime(LocalTime.of(13, 0));
        List<Booking> oldBookingList = Arrays.asList(booking1, booking2, booking5);

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

        when(bookingRepository.findByBookingDateAndStaffIdInAndCustomerAndStartTimeBetween(
                oldBooking.getBookingDate(), staffIdList, customerId, oldBooking.getStartTime(), oldBooking.getEndTime()))
                .thenReturn(oldBookingList);

        // when
        bookingUpdateService.update(oldBooking, updatedBooking, staffIdList, customerId);

        //then
        verify(bookingRepository).saveAll(oldBookingList);
        verify(bookingAddService).add(updatedBooking, staffIdList, customerId);
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
        Throwable throwable = catchThrowable(() -> bookingUpdateService.update(booking, booking, staffIdList, customerId));

        //then
        assertThat(throwable.getClass()).isEqualTo(CleaningAppDomainNotFoundException.class);
    }

}
