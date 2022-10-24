package com.justlife.cleaning.service;

import com.justlife.cleaning.common.AbstractIntegrationTest;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import com.justlife.cleaning.repository.BookingRepository;
import com.justlife.cleaning.utils.BookingTestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestIntegrationBookingService extends AbstractIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    @Transactional
    public void test_find_by_booking_date() {
        LocalDate bookingDate = LocalDate.of(2022, 11, 01);

        Page<Booking> bookingPage = bookingService.findByBookingDate(bookingDate, 0, 10);

        assertThat(bookingPage.getContent().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void test_find_by_given_time_slot() {
        CheckTimeSlotFilterRequest request = new CheckTimeSlotFilterRequest();
        request.setBookingDate(LocalDate.of(2022, 11, 1));
        request.setStartTime(LocalTime.of(15, 0));
        request.setDuration(2);
        request.setPage(0);
        request.setSize(10);

        Page<Booking> bookingPage = bookingService.findByGivenTimeSlot(request);

        assertThat(bookingPage.getContent().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void test_save_all() {
        Booking booking1 = BookingTestBuilder.buildBookingForDB();
        booking1.setId(null);
        List<Booking> bookingList = Arrays.asList(booking1);

        bookingService.saveAll(bookingList);

        Page<Booking> bookingPage = bookingRepository.findByBookingDateAndCustomerIsNull(LocalDate.of(2022, 10, 29), null);
        assertThat(bookingPage.getContent().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void test_add_booking() {
        Booking booking = BookingTestBuilder.buildBookingForAdding();

        bookingService.addBooking(booking, Arrays.asList(100001L), 100001L);

        List<Booking> bookingList = bookingRepository.findByBookingDateAndStaffIdInAndCustomerAndStartTimeBetween(
                booking.getBookingDate(), Arrays.asList(100001L), 100001L, booking.getStartTime(), booking.getEndTime());
        assertThat(bookingList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void test_update_booking() {
        Booking oldBooking = BookingTestBuilder.buildOldBookingForUpdating();
        Booking newBooking = BookingTestBuilder.buildNewBookingForUpdating();

        bookingService.updateBooking(oldBooking, newBooking, Arrays.asList(100001L), 100001L);

        List<Booking> bookingList = bookingRepository.findByBookingDateAndStaffIdInAndCustomerAndStartTimeBetween(
                newBooking.getBookingDate(), Arrays.asList(100001L), 100001L, newBooking.getStartTime(), newBooking.getEndTime());
        assertThat(bookingList.size()).isEqualTo(3);
    }

}
