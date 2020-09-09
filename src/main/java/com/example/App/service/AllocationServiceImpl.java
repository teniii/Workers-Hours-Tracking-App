package com.example.App.service;

import com.example.App.Entity.Allocation;
import com.example.App.Entity.Angajat;
import com.example.App.Entity.Project;
import com.example.App.Repository.AllocationRepository;
import com.example.App.Repository.AngajatRepository;
import com.example.App.Repository.ProjectRepository;
import com.example.App.compositeId.AllocationId;
import com.example.App.util.CrmAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;

@Service
public class AllocationServiceImpl implements AllocationService {

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    AngajatRepository angajatRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Allocation findByAllocationIdUserIdAndAllocationId_ProjectId(int user, int project) {
        return allocationRepository.findByAllocationIdUserIdAndAllocationId_ProjectId(user,project);
    }

    @Override
    public Allocation findByAllocationId_ProjectIdAndAllocationId_Date(int project, Date date) {
        return allocationRepository.findByAllocationId_ProjectIdAndAllocationId_Date(project,date);
    }

    @Override
    @Transactional
    public void deleteByAllocationId_DateAndAllocationId_ProjectIdAndAllocationId_UserId(Date date, int project, int user) {
        allocationRepository.deleteByAllocationId_DateAndAllocationId_ProjectIdAndAllocationId_UserId(date,project,user);
    }

    @Override
    public void save(CrmAllocation crmAllocation) {
        Allocation allocation = new Allocation();
        AllocationId allocationId = new AllocationId();

        Angajat angajat = angajatRepository.findByCNP(crmAllocation.getCNP());
        allocationId.setUser(angajat);


        Project project = projectRepository.findById(crmAllocation.getId_project());
        allocationId.setProject(project);

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        allocationId.setDate(date);

        allocation.setAllocationId(allocationId);

        allocation.setHours(crmAllocation.getHours());
        allocation.setComments(crmAllocation.getComments());

        allocationRepository.save(allocation);

    }

    @Override
    public void save(Allocation allocation, Date tempDate, int tempProjectId) {
        allocation.getAllocationId().setDate(tempDate);
        allocationRepository.save(allocation);
    }

    @Override
    public Allocation findByAllocationId_ProjectIdAndAllocationId_DateAndAllocationId_UserId(int project, Date date, int user) {
        return allocationRepository.findByAllocationId_ProjectIdAndAllocationId_DateAndAllocationId_UserId(project,date,user);
    }

    @Override
    public void save(CrmAllocation crmAllocation, int id_user) {
        Allocation allocation = new Allocation();
        AllocationId allocationId = new AllocationId();

        Angajat angajat = angajatRepository.findByCNP(crmAllocation.getCNP());
        allocationId.setUser(angajat);


        Project project = projectRepository.findById(crmAllocation.getId_project());
        allocationId.setProject(project);

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        allocationId.setDate(date);

        allocation.setAllocationId(allocationId);

        allocation.setHours(crmAllocation.getHours());
        allocation.setComments(crmAllocation.getComments());
        allocation.getAllocationId().getUser().setId(id_user);
        allocationRepository.save(allocation);
    }
}
