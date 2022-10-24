package com.justlife.cleaning.repository;

import com.justlife.cleaning.common.AbstractIntegrationTest;
import com.justlife.cleaning.model.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TestIntegrationBookingRepository extends AbstractIntegrationTest {

    @Autowired
    private BookingRepository bookingRepositoryRepository;

    @Test
    public void test_find_by_booking_date_and_customer_and_start_time_between() {
        LocalDate bookingDate = LocalDate.of(2022, 11, 1);
        LocalTime startTime = LocalTime.of(12, 00, 00);
        LocalTime endTime = LocalTime.of(14, 00, 00);
        List<Booking> bookingList = bookingRepositoryRepository.findByBookingDateAndStaffIdInAndCustomerAndStartTimeBetween(
                bookingDate, Arrays.asList(100001L), 100001L, startTime, endTime);

        assertThat(bookingList.size()).isEqualTo(3);
    }

    @Test
    public void test_find_by_booking_date_and_staff_and_start_time_between() {
        LocalDate bookingDate = LocalDate.of(2022, 11, 1);
        LocalTime startTime = LocalTime.of(14, 00, 00);
        LocalTime endTime = LocalTime.of(16, 00, 00);
        List<Booking> bookingList = bookingRepositoryRepository.findByBookingDateAndStaffInAndStartTimeBetweenAndCustomerIsNull(
                bookingDate, Arrays.asList(100001L), startTime, endTime);

        assertThat(bookingList.size()).isEqualTo(1);
    }

    @Test
    public void test_find_by_booking_date_and_start_time_between() {
        LocalDate bookingDate = LocalDate.of(2022, 11, 1);
        LocalTime startTime = LocalTime.of(15, 00, 00);
        LocalTime endTime = LocalTime.of(17, 00, 00);
        Page<Booking> bookingList = bookingRepositoryRepository.findByBookingDateAndStartTimeBetweenAndCustomerIsNull(
                bookingDate, startTime, endTime, PageRequest.of(0, 10));

        assertThat(bookingList.getContent().size()).isEqualTo(1);
    }

    @Test
    public void test_find_by_booking_date_and_customer_is_null() {
        LocalDate bookingDate = LocalDate.of(2022, 11, 1);
        Page<Booking> bookingList = bookingRepositoryRepository.findByBookingDateAndCustomerIsNull(
                bookingDate, PageRequest.of(0, 10));

        assertThat(bookingList.getContent().size()).isEqualTo(1);
    }

}