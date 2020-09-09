package com.example.App.controller;

import com.example.App.Entity.Company;
import com.example.App.service.CompanyService;
import com.example.App.util.CrmCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegistrationCompanyController {

    @Autowired
    private CompanyService companyService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyRegisterPage(Model theModel) {
        theModel.addAttribute("crmCompany", new CrmCompany());

        return "registration-formCompany";
    }

    @PostMapping("processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("crmCompany") CrmCompany theCrmCompany,
            BindingResult theBindingResult,
            Model theModel) {
        String CUI = theCrmCompany.getCUI();
        logger.info("Processing registration form for: " + CUI);

        //form validation
        if(theBindingResult.hasErrors()) {
            return "regitration-form";
        }

        //check the database if company already exists
        Company existing = companyService.findByCUI(CUI);
        if(existing != null ) {
            theModel.addAttribute("crmCompany", new CrmCompany());
            theModel.addAttribute("registrationError", "CUI already exists. ");

            logger.warning("CUI already exists");
            return "registration-formCompany";
        }
        //create company account
        companyService.save(theCrmCompany);

        logger.info("Successfully created company: " + CUI);

        return "loginCompany";
    }

}
