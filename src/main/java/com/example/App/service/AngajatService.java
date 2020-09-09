package com.example.App.service;

import com.example.App.Entity.Angajat;
import com.example.App.util.CrmAngajat;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AngajatService extends UserDetailsService {
    public Angajat findByCNP(String CNP);

    void save(CrmAngajat crmAngajat);
}
