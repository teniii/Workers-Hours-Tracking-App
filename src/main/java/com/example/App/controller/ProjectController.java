package com.example.App.controller;

import com.example.App.Entity.Project;
import com.example.App.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/project/all")
    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }
//    @GetMapping("/project/{param}")
//    public Project getProject(@PathVariable int param){
//        return projectRepository.findById(param).get();
//    }
}
