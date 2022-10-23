package com.justlife.cleaning.controller;

import com.justlife.cleaning.model.dto.AvailableStaffDTO;
import com.justlife.cleaning.model.dto.AvailableStaffTimeDTO;
import com.justlife.cleaning.model.dto.CheckDateDTO;
import com.justlife.cleaning.model.dto.CheckTimeSlotDTO;
import com.justlife.cleaning.model.pageable.PageableResult;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import com.justlife.cleaning.service.AvailabilityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/findAvailableTimes")
    public PageableResult<AvailableStaffTimeDTO> findAvailableTimes(@RequestBody @Valid CheckDateDTO checkDateDTO) {
        return availabilityService.findAvailableTimes(checkDateDTO.getBookingDate(), checkDateDTO.getPage(), checkDateDTO.getSize());
    }

    @PostMapping("/checkTimeSlotAvailable")
    public PageableResult<AvailableStaffDTO> checkTimeSlotAvailable(@RequestBody @Valid CheckTimeSlotDTO checkTimeSlotDTO) {
        return availabilityService.checkTimeSlotAvailable(CheckTimeSlotFilterRequest.from(checkTimeSlotDTO));
    }

}