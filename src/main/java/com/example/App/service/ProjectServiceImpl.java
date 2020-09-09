package com.example.App.service;

import com.example.App.Entity.Company;
import com.example.App.Entity.Project;
import com.example.App.Repository.CompanyRepository;
import com.example.App.Repository.ProjectRepository;
import com.example.App.util.CrmProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Project findById(int id) {
        return projectRepository.findById(id);
    }

    @Override
    public void save(CrmProject crmProject) {
        Project project = new Project();
        Company company = companyRepository.findById(crmProject.getCompany_id());
        project.setCompany(company);
        project.setName(crmProject.getName());

        projectRepository.save(project);
    }
}
