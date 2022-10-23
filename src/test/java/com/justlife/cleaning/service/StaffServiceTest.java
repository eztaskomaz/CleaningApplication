package com.justlife.cleaning.service;

import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.repository.StaffRepository;
import com.justlife.cleaning.utils.StaffTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

    @InjectMocks
    private StaffService staffService;

    @Mock
    private StaffRepository staffRepository;

    @Test
    public void it_should_find_by_id_list() {
        // given
        List<Long> idList = Arrays.asList(1L, 2L);
        List<Staff> staffList = Arrays.asList(StaffTestBuilder.buildStaff(1L), StaffTestBuilder.buildStaff(2L));

        when(staffRepository.findAllById(idList)).thenReturn(staffList);

        // when
        List<Staff> staffs = staffService.findByIdList(idList);

        // then
        verify(staffRepository).findAllById(idList);
        assertThat(staffs.get(0).getId()).isEqualTo(1L);
        assertThat(staffs.get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void it_should_find_all() {
        // given
        List<Staff> staffList = Arrays.asList(StaffTestBuilder.buildStaff(1L), StaffTestBuilder.buildStaff(2L));

        when(staffRepository.findAll()).thenReturn(staffList);

        // when
        List<Staff> staffs = staffService.findAll();

        // then
        verify(staffRepository).findAll();
        assertThat(staffs.get(0).getId()).isEqualTo(1L);
        assertThat(staffs.get(1).getId()).isEqualTo(2L);
    }

}
