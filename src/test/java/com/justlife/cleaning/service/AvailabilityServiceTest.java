package com.justlife.cleaning.service;

import com.justlife.cleaning.common.exception.CleaningAppBusinessException;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.dto.AvailableStaffDTO;
import com.justlife.cleaning.model.dto.AvailableStaffTimeDTO;
import com.justlife.cleaning.model.pageable.PageableResult;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import com.justlife.cleaning.utils.BookingTestBuilder;
import com.justlife.cleaning.utils.CheckTimeSlotFilterRequestTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceTest {

    @InjectMocks
    private AvailabilityService availabilityService;

    @Mock
    private BookingService bookingService;

    @Test
    public void it_should_find_available_times() {
        // given
        LocalDate bookingDate = LocalDate.of(2022, 10, 30);
        Integer page = 1;
        Integer size = 10;

        List<Booking> bookingList = Collections.singletonList(BookingTestBuilder.buildBooking(1L));
        Page<Booking> pagedResponse = new PageImpl(bookingList);

        when(bookingService.findByBookingDate(bookingDate, page, size)).thenReturn(pagedResponse);

        // when
        PageableResult<AvailableStaffTimeDTO> availableTimes = availabilityService.findAvailableTimes(bookingDate, page, size);

        // then
        verify(bookingService).findByBookingDate(bookingDate, page, size);
        assertThat(availableTimes.getContent().size()).isEqualTo(1);
    }

    @Test
    public void it_should_not_find_available_times() {
        // given
        LocalDate bookingDate = LocalDate.of(2022, 10, 30);
        Integer page = 1;
        Integer size = 10;

        Page<Booking> pagedResponse = new PageImpl(Collections.emptyList());

        when(bookingService.findByBookingDate(bookingDate, page, size)).thenReturn(pagedResponse);

        // when
        Throwable throwable = catchThrowable(() -> availabilityService.findAvailableTimes(bookingDate, page, size));

        // then
        verify(bookingService).findByBookingDate(bookingDate, page, size);
        assertThat(throwable.getClass()).isEqualTo(CleaningAppBusinessException.class);
    }

    @Test
    public void it_should_find_check_time_slot_available() {
        // given
        LocalDate bookingDate = LocalDate.of(2022, 10, 30);
        Integer page = 1;
        Integer size = 10;

        CheckTimeSlotFilterRequest request = CheckTimeSlotFilterRequestTestBuilder.build(bookingDate, page, size);

        List<Booking> bookingList = Collections.singletonList(BookingTestBuilder.buildBooking(1L));
        Page<Booking> pagedResponse = new PageImpl(bookingList);

        when(bookingService.findByGivenTimeSlot(request)).thenReturn(pagedResponse);

        // when
        PageableResult<AvailableStaffDTO> availableTimes = availabilityService.checkTimeSlotAvailable(request);

        // then
        verify(bookingService).findByGivenTimeSlot(request);
        assertThat(availableTimes.getContent().size()).isEqualTo(1);
    }

    @Test
    public void it_should_find_not_check_time_slot_available() {
        // given
        LocalDate bookingDate = LocalDate.of(2022, 10, 30);
        Integer page = 1;
        Integer size = 10;

        CheckTimeSlotFilterRequest request = new CheckTimeSlotFilterRequest();
        request.setBookingDate(bookingDate);
        request.setStartTime(LocalTime.of(10, 0));
        request.setDuration(2);
        request.setPage(page);
        request.setSize(size);

        Page<Booking> pagedResponse = new PageImpl(Collections.emptyList());

        when(bookingService.findByGivenTimeSlot(request)).thenReturn(pagedResponse);

        // when
        Throwable throwable = catchThrowable(() -> availabilityService.checkTimeSlotAvailable(request));

        // then
        verify(bookingService).findByGivenTimeSlot(request);
        assertThat(throwable.getClass()).isEqualTo(CleaningAppBusinessException.class);
    }

}