package com.justlife.cleaning.service;

import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import com.justlife.cleaning.repository.BookingRepository;
import com.justlife.cleaning.utils.BookingTestBuilder;
import com.justlife.cleaning.utils.CheckTimeSlotFilterRequestTestBuilder;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingAddService bookingAddService;

    @Mock
    private BookingUpdateService bookingUpdateService;

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
        List<Long> staffIdList = Arrays.asList(1L);
        Long customerId = 123L;

        // when
        bookingService.addBooking(booking, staffIdList, customerId);

        //then
        verify(bookingAddService).add(booking, staffIdList, customerId);
    }


    @Test
    public void it_should_update_booking() {
        // given
        Booking oldBooking = BookingTestBuilder.buildBooking(1L);
        Booking updatedBooking = BookingTestBuilder.buildBooking(1L);
        List<Long> staffIdList = Arrays.asList(1L);
        Long customerId = 123L;

        // when
        bookingService.updateBooking(oldBooking, updatedBooking, staffIdList, customerId);

        //then
        verify(bookingUpdateService).update(oldBooking, updatedBooking, staffIdList, customerId);
    }

}
