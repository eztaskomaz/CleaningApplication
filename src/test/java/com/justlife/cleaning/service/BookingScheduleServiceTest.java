package com.justlife.cleaning.service;

import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.utils.StaffTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingScheduleServiceTest {

    @InjectMocks
    private BookingScheduleService bookingScheduleService;

    @Mock
    private StaffService staffService;

    @Mock
    private BookingService bookingService;

    @Test
    public void it_create_booking_schedule_every_sunday_for_the_next_week() {
        // given
        List<Staff> staffList = Arrays.asList(StaffTestBuilder.buildStaff(1L), StaffTestBuilder.buildStaff(2L));
        when(staffService.findAll()).thenReturn(staffList);

        // when
        bookingScheduleService.createBookingScheduleEverySundayForTheNextWeek();

        // then
        bookingService.saveAll(Mockito.any());
    }

}
