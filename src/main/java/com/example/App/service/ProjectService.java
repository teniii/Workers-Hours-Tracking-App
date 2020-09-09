package com.example.App.service;

import com.example.App.Entity.Project;
import com.example.App.util.CrmProject;

public interface ProjectService {
    public Project findById(int id);
    public void save(CrmProject crmProject);

}
