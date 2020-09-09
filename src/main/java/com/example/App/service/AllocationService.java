package com.example.App.service;

import com.example.App.Entity.Allocation;
import com.example.App.util.CrmAllocation;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.Date;

public interface AllocationService {

    public Allocation findByAllocationIdUserIdAndAllocationId_ProjectId(int user, int project);
    Allocation findByAllocationId_ProjectIdAndAllocationId_Date(int project, Date date);
    void deleteByAllocationId_DateAndAllocationId_ProjectIdAndAllocationId_UserId(Date date, int project, int user);
    Allocation findByAllocationId_ProjectIdAndAllocationId_DateAndAllocationId_UserId(int project, Date date, int user);
    public void save(CrmAllocation crmAllocation);
    void save(CrmAllocation crmAllocation, int id_user);
    void save(Allocation allocation, Date tempDate, int tempProjectId);

}
