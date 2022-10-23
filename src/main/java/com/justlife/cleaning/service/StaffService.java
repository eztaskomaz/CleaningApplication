package com.justlife.cleaning.service;

import com.justlife.cleaning.model.Staff;
import com.justlife.cleaning.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Transactional(readOnly = true)
    public List<Staff> findByIdList(List<Long> idList) {
        return staffRepository.findAllById(idList);
    }

    @Transactional(readOnly = true)
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

}
