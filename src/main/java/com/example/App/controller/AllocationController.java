package com.example.App.controller;

import com.example.App.Entity.Allocation;
import com.example.App.Entity.Company;
import com.example.App.Entity.Project;
import com.example.App.Entity.Angajat;
import com.example.App.Repository.AllocationRepository;
import com.example.App.Repository.CompanyRepository;
import com.example.App.Repository.ProjectRepository;
import com.example.App.Repository.AngajatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/allocation")
public class AllocationController {

    @Autowired
    AllocationRepository allocationRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AngajatRepository userRepository;
    @Autowired
    CompanyRepository companyRepository;


    @GetMapping("/check")
    public List<Angajat> getUser(){
        return userRepository.findAll();
    }

    @GetMapping("/check2")
    public List<Company> getComp(){
        return companyRepository.findAll();
    }

    @GetMapping("/check3")
    public List<Project> getProj(){
        return projectRepository.findAll();
    }

    @GetMapping("/allocations/{param}")
    public List<Allocation> getAllocations(@PathVariable int param) {
        //User usr = userRepository.findById(1).get();
        return allocationRepository.findByAllocationIdUserId(param);

    }

    @GetMapping("/allocations")
    public List<Allocation> getAllAllocations() {
        //User usr = userRepository.findById(1).get();
        return allocationRepository.findAll();

    }
}
