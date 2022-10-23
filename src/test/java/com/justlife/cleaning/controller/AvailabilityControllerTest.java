package com.justlife.cleaning.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.justlife.cleaning.model.dto.AvailableStaffDTO;
import com.justlife.cleaning.model.dto.AvailableStaffTimeDTO;
import com.justlife.cleaning.model.dto.CheckDateDTO;
import com.justlife.cleaning.model.dto.CheckTimeSlotDTO;
import com.justlife.cleaning.model.pageable.PageableResult;
import com.justlife.cleaning.model.pojo.CheckTimeSlotFilterRequest;
import com.justlife.cleaning.service.AvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AvailabilityControllerTest {

    @InjectMocks
    private AvailabilityController controller;

    @Mock
    private AvailabilityService availabilityService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void it_should_find_available_times() throws Exception {
        //given
        CheckDateDTO checkDateDTO = new CheckDateDTO();
        checkDateDTO.setBookingDate(LocalDate.of(2022, 10, 30));
        checkDateDTO.setPage(0);
        checkDateDTO.setSize(10);

        AvailableStaffTimeDTO availableStaffTimeDTO = new AvailableStaffTimeDTO();
        availableStaffTimeDTO.setAvailableTime(LocalTime.of(10, 0));
        AvailableStaffDTO availableStaffDTO = new AvailableStaffDTO();
        availableStaffDTO.setName("name");
        availableStaffDTO.setSurname("surname");
        availableStaffTimeDTO.setStaff(availableStaffDTO);
        PageableResult<AvailableStaffTimeDTO> pageableResult = new PageableResult<>(1, 0, 1, Collections.singletonList(availableStaffTimeDTO));

        when(availabilityService.findAvailableTimes(checkDateDTO.getBookingDate(), checkDateDTO.getPage(), checkDateDTO.getSize()))
                .thenReturn(pageableResult);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonBody = objectMapper.writeValueAsString(checkDateDTO);

        //when
        ResultActions resultActions = mockMvc.perform(post("/availability/findAvailableTimes")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        resultActions.andExpect(jsonPath("$.content[0].staff.name").value("name"));
        resultActions.andExpect(jsonPath("$.content[0].staff.surname").value("surname"));
    }

    @Test
    void it_should_check_time_slot_available() throws Exception {
        //given
        CheckTimeSlotDTO checkTimeSlotDTO = new CheckTimeSlotDTO();
        checkTimeSlotDTO.setBookingDate(LocalDate.of(2022, 10, 30));
        checkTimeSlotDTO.setStartTime(10);
        checkTimeSlotDTO.setDuration(2);
        checkTimeSlotDTO.setPage(0);
        checkTimeSlotDTO.setSize(10);

        AvailableStaffDTO availableStaffDTO = new AvailableStaffDTO();
        availableStaffDTO.setName("name");
        availableStaffDTO.setSurname("surname");
        PageableResult<AvailableStaffDTO> pageableResult = new PageableResult<>(1, 0, 1, Collections.singletonList(availableStaffDTO));

        when(availabilityService.checkTimeSlotAvailable(Mockito.any(CheckTimeSlotFilterRequest.class)))
                .thenReturn(pageableResult);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonBody = objectMapper.writeValueAsString(checkTimeSlotDTO);

        //when
        ResultActions resultActions = mockMvc.perform(post("/availability/checkTimeSlotAvailable")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        resultActions.andExpect(jsonPath("$.content[0].name").value("name"));
        resultActions.andExpect(jsonPath("$.content[0].surname").value("surname"));
    }

}
