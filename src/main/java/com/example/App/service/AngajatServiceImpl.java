package com.example.App.service;

import com.example.App.Entity.Angajat;
import com.example.App.Repository.AngajatRepository;
import com.example.App.Repository.CompanyRepository;
import com.example.App.util.CrmAngajat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.App.util.ApplicationRoles.*;

@Service
public class AngajatServiceImpl implements AngajatService {

    @Autowired
    private AngajatRepository angajatRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Angajat findByCNP(String CNP) {
        return angajatRepository.findByCNP(CNP);
    }

    @Override
    public UserDetails loadUserByUsername(String CNP) throws UsernameNotFoundException {
        Angajat angajat = angajatRepository.findByCNP(CNP);
        if(angajat == null) {
            throw new UsernameNotFoundException("Invalid CNP or password.");
        }

        return new org.springframework.security.core.userdetails.User(angajat.getCNP(), angajat.getPassword(),
                ANGAJAT.getGrantedAuthorities());
    }

    @Override
    public void save(CrmAngajat crmAngajat) {
        Angajat angajat = new Angajat();

        angajat.setCNP(crmAngajat.getCNP());
        angajat.setFirstname(crmAngajat.getFirstname());
        angajat.setLastname(crmAngajat.getLastname());
        angajat.setEmail(crmAngajat.getEmail());
        angajat.setPassword(passwordEncoder.encode(crmAngajat.getPassword()));
        angajatRepository.save(angajat);
    }
}
