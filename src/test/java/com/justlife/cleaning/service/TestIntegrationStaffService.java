package com.justlife.cleaning.service;

import com.justlife.cleaning.common.AbstractIntegrationTest;
import com.justlife.cleaning.model.Staff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestIntegrationStaffService extends AbstractIntegrationTest {

    @Autowired
    private StaffService staffService;

    @Test
    @Transactional
    public void test_find_by_id_list() {
        List<Staff> list = staffService.findByIdList(Arrays.asList(100001L, 100107L));

        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void test_find_all() {
        List<Staff> list = staffService.findAll();

        assertThat(list.size()).isEqualTo(25);
    }
}
