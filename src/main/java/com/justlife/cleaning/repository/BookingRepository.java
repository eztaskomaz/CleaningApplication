package com.justlife.cleaning.repository;

import com.justlife.cleaning.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "select * from booking where\n" +
            "        booking_date = :bookingDate and\n" +
            "        customer_id = :customerId and\n" +
            "        start_time >= :startTime and start_time <= :endTime", nativeQuery = true)
    List<Booking> findByBookingDateAndCustomerAndStartTimeBetween(@Param("bookingDate") LocalDate bookingDate, @Param("customerId") Long customerId, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);

    @Query(value = "select * from booking where\n" +
            "        booking_date = :bookingDate and\n" +
            "        staff_id in :staffIdList and\n" +
            "        start_time >= :startTime and start_time <= :endTime", nativeQuery = true)
    List<Booking> findByBookingDateAndStaffInAndStartTimeBetween(@Param("bookingDate") LocalDate bookingDate, @Param("staffIdList") List<Long> staffIdList, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);

    Page<Booking> findByBookingDateAndStartTimeBetween(LocalDate bookingDate, LocalTime startTime, LocalTime endTime, Pageable pageable);

    Page<Booking> findByBookingDateAndCustomerIsNull(LocalDate bookingDate, Pageable pageable);
}
