package com.example.App.service;

import com.example.App.Entity.Company;
import com.example.App.util.CrmCompany;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CompanyService extends UserDetailsService {

    public Company findByCUI(String CUI);

    public void save(CrmCompany crmCompany);

}
