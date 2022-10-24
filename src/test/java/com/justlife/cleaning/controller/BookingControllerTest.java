package com.justlife.cleaning.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.justlife.cleaning.model.Booking;
import com.justlife.cleaning.model.dto.AddBookingDTO;
import com.justlife.cleaning.model.dto.UpdateBookingDTO;
import com.justlife.cleaning.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @InjectMocks
    private BookingController controller;

    @Mock
    private BookingService bookingService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void it_should_add_booking() throws Exception {
        //given
        AddBookingDTO addBookingDTO = new AddBookingDTO();
        addBookingDTO.setBookingDate(LocalDate.of(2022, 10, 30));
        addBookingDTO.setStartTime(10);
        addBookingDTO.setDuration(2);
        addBookingDTO.setStaffIdList(Arrays.asList(1L));
        addBookingDTO.setCustomerId(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonBody = objectMapper.writeValueAsString(addBookingDTO);

        //when
        mockMvc.perform(post("/booking/addBooking")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(bookingService).addBooking(any(Booking.class), eq(addBookingDTO.getStaffIdList()), eq(addBookingDTO.getCustomerId()));
    }

    @Test
    void it_should_update_booking() throws Exception {
        //given
        UpdateBookingDTO updateBookingDTO = new UpdateBookingDTO();
        updateBookingDTO.setBookingDate(LocalDate.of(2022, 10, 30));
        updateBookingDTO.setNewStartTime(10);
        updateBookingDTO.setNewDuration(2);
        updateBookingDTO.setOldStartTime(8);
        updateBookingDTO.setOldDuration(2);
        updateBookingDTO.setCustomerId(1L);
        updateBookingDTO.setStaffIdList(Arrays.asList(1L));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonBody = objectMapper.writeValueAsString(updateBookingDTO);

        //when
        mockMvc.perform(post("/booking/updateBooking")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(bookingService).updateBooking(any(Booking.class), any(Booking.class), eq(updateBookingDTO.getStaffIdList()), eq(updateBookingDTO.getCustomerId()));
    }

}
