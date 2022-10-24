package com.justlife.cleaning.service;

import com.justlife.cleaning.common.AbstractIntegrationTest;
import com.justlife.cleaning.model.dto.AvailableStaffDTO;
import com.justlife.cleaning.model.dto.AvailableStaffTimeDTO;
import com.justlife.cleaning.model.pageable.PageableResult;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestIntegrationAvailabilityService extends AbstractIntegrationTest {

    @Autowired
    private AvailabilityService availabilityService;

    @Test
    @Transactional
    public void test_find_available_times() {
        PageableResult<AvailableStaffTimeDTO> availableTimes =
                availabilityService.findAvailableTimes(LocalDate.of(2022, 11, 01), 0, 10);

        assertThat(availableTimes.getContent().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void test_check_time_slot_available() {
        CheckTimeSlotFilterRequest request = new CheckTimeSlotFilterRequest();
        request.setBookingDate(LocalDate.of(2022, 11, 1));
        request.setStartTime(LocalTime.of(15, 0));
        request.setDuration(2);
        request.setPage(0);
        request.setSize(10);

        PageableResult<AvailableStaffDTO> availableStaff =
                availabilityService.checkTimeSlotAvailable(request);

        assertThat(availableStaff.getContent().size()).isEqualTo(1);
    }
}
