package com.example.App.service;

import com.example.App.Entity.Company;
import com.example.App.Repository.CompanyRepository;
import com.example.App.util.CrmCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.App.util.ApplicationRoles.*;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Company findByCUI(String CUI) {
        return companyRepository.findByCUI(CUI);
    }

    @Override
    public UserDetails loadUserByUsername(String CUI) throws UsernameNotFoundException {
        Company company = companyRepository.findByCUI(CUI);
        if (company == null) {
            throw new UsernameNotFoundException("Invalid CUI or password.");
        }

        return new org.springframework.security.core.userdetails.User(company.getCUI(), company.getPassword(),
                COMPANY.getGrantedAuthorities());
    }

    @Override
    public void save(CrmCompany crmCompany) {
        Company company = new Company();

        company.setCUI(crmCompany.getCUI());
        company.setPassword(passwordEncoder.encode(crmCompany.getPassword()));
        company.setName(crmCompany.getName());
        company.setEmail(crmCompany.getEmail());

        companyRepository.save(company);
    }
}
