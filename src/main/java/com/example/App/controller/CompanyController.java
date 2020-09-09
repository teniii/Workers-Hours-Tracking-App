package com.example.App.controller;

import com.example.App.Entity.*;
import com.example.App.Repository.AllocationRepository;
import com.example.App.Repository.AngajatRepository;
import com.example.App.Repository.CompanyRepository;
import com.example.App.Repository.ProjectRepository;
import com.example.App.service.AllocationService;
import com.example.App.service.AngajatService;
import com.example.App.service.ProjectService;
import com.example.App.util.*;
import com.example.App.util.moreUtils.HoursPerAngajat;
import com.example.App.util.moreUtils.HoursPerProject;
import com.example.App.util.moreUtils.HoursPerProjectPerAngajat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.util.*;

@Controller
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private AllocationService allocationService;

    @Autowired
    AllocationRepository allocationRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AngajatService angajatService;

    @Autowired
    private AngajatRepository angajatRepository;

    //    @RequestMapping(value = "/company/projects", method = RequestMethod.GET)
//    @ResponseBody
//    @ModelAttribute("projects")
//    public String  getProjects(Model model, Principal principal) {
//        //id in form html
//        Company companyCautat = companyRepository.findByCUI(principal.getName());
//        model.addAttribute("projects",projectRepository.findAllByCompanyId(companyCautat.getId()));
////        return projectRepository.findAllByCompanyId(companyCautat.getId());
//        return "ccompanyHome";
//    }
    private int company_id;
    private String company_CUI;
    @GetMapping("/companyHome")
    public String showAllProjects(Model model, Principal principal) {
        Company company = companyRepository.findByCUI(principal.getName());
        model.addAttribute(
                "projects", projectRepository.
                        findAllByCompanyId(company.getId()));
        company_id = company.getId();

        //int id = 1;
        String id = principal.getName(); //cui companie
        Company company2 = companyRepository.findByCUI(id);
        company_CUI = company2.getCUI();
        List<Project> list = projectRepository.findAllByCompanyId(company2.getId());
        List<Allocation> allocations = allocationRepository.findDistinctByAllocationId_ProjectIn(list);
        Set<Angajat> angajats = new HashSet<>();
        for (Allocation temp : allocations) {
            angajats.add(temp.getAllocationId().getUser());
        }
        model.addAttribute("angajati", angajats);


        return "companyHome";
    }

    @GetMapping("/company/allocationRegister")
    public String registerAllocation(Model model) {
        model.addAttribute("crmAllocation", new CrmAllocation());

        return "company/registration-formAllocation";
    }

    @PostMapping("/company/processRegistrationAllocationForm")
    public String processRegistrationAllocationForm(
            @Valid @ModelAttribute("crmAllocation") CrmAllocation crmAllocation,
            Model model) {
        int id_project = crmAllocation.getId_project();
        String CNP_user = crmAllocation.getCNP();

        Angajat angajat = angajatRepository.findByCNP(CNP_user);
        int id_user = angajat.getId();


        Allocation existing = allocationService.findByAllocationIdUserIdAndAllocationId_ProjectId(id_user, id_project);
        List<Project> proiecteleCompaniei = projectRepository.findAllByCompanyId(company_id);

        Project project = projectRepository.findById(id_project);
        if (proiecteleCompaniei.contains(project)) {
            System.out.println(project.getName());
        } else {
            model.addAttribute("crmAllocation", new CrmAllocation());
            model.addAttribute("registrationError", "The project you're trying to allocate is not yours");

            return "company/registration-formAllocation";
        }

        if (existing != null) {
            model.addAttribute("crmAllocation", new CrmAllocation());
            model.addAttribute("registrationError", "User already added to this project");

            return "company/registration-formAllocation";
        }
        allocationService.save(crmAllocation);


        return "redirect:/companyHome";
    }

    @GetMapping("/company/projectRegister")
    public String registerProject(Model model) {
        model.addAttribute("crmProject", new CrmProject());

        return "company/registration-formProject";
    }

    @PostMapping("/company/processRegistrationProjectForm")
    public String processRegistrationProjectForm(
            @Valid @ModelAttribute("crmProject") CrmProject crmProject) {

        crmProject.setCompany_id(company_id);
        projectService.save(crmProject);

        return "redirect:/companyHome";
    }

    @GetMapping("/company/angajatRegister")
    public String registerAngajat(Model model) {
        model.addAttribute("crmAngajat", new CrmAngajat());

        return "company/registration-formAngajat";
    }

    @PostMapping("/company/processRegistrationAngajatForm")
    public String processRegistrationAngajatForm(
            @Valid @ModelAttribute("crmAngajat") CrmAngajat crmAngajat,
            Model model) {
        String CNP = crmAngajat.getCNP();
        Angajat existingAngajat = angajatService.findByCNP(CNP);
        if (existingAngajat != null) {
            model.addAttribute("crmAngajat", new CrmAngajat());
            model.addAttribute("registrationError", "This Angajat's CNP is already added");

            return "company/registration-formAngajat";
        }

        angajatService.save(crmAngajat);

        String destination = crmAngajat.getEmail();
        Company senderCompany = companyRepository.findByCUI(company_CUI);
        String sender = senderCompany.getEmail();
        String subject = "You're hired!";
        String message = "We gladly confirm you that you are a new member of our company, \n";

        EmailSender.send(destination,sender,subject,message + senderCompany.getName());

        return "redirect:/companyHome";
    }

    @GetMapping(value = "company/delete/{id}")
    public String delete(@PathVariable int id) {
        angajatRepository.deleteById(id);
        return "redirect:/companyHome";
    }

    private int tempId = 0;

//    @GetMapping(value = "company/email/{id}")
//    public String delete(@PathVariable int id) {
//        angajatRepository.deleteById(id);
//        return "redirect:/companyHome";
//    }

    @GetMapping(value = "company/mail/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("crmMail", new CrmMail());

        tempId = id;
        return "/company/mailForm";
    }

    @PostMapping("/company/processMailForm")
    public String processMailForm(
            @Valid @ModelAttribute("crmMail") CrmMail crmMail,
            Model model) {

        EmailSender emailSender = null;

        Angajat angajat = angajatRepository.findById(tempId);
        tempId = 0;
        Company company = companyRepository.findById(company_id);

        String toMailAngajat = angajat.getEmail();
        String fromMailCompany = company.getEmail();

        emailSender.send(toMailAngajat, fromMailCompany,crmMail.getSubject(), crmMail.getMessage());

        return "redirect:/companyHome";
    }

    @GetMapping("/company/stats")
    public String getStats(Model model) {

        List <List<Integer>> doubleList = companyRepository.findHoursPerProject(company_id);

        List <HoursPerProject> finalList1 = new ArrayList<>();

        String project = "";
        String fullName = "";
        int hours = 0;
        try {
            for (List<Integer> list : doubleList) {
                project = projectRepository.findById(list.get(0)).get().getName();
                hours = list.get(1);
                finalList1.add(new HoursPerProject(project, hours));
            }
            model.addAttribute("listHoursPerProject", finalList1);
        } catch (Exception e){
            model.addAttribute("listHoursPerProject", 0);
            System.out.println(e.getMessage());
        }

            //second

            doubleList = companyRepository.findHoursPerUser(company_id);
            List<HoursPerAngajat> finalList2 = new ArrayList<>();
            try {
                for (List<Integer> list : doubleList) {
                    fullName = angajatRepository.findById(list.get(0)).get().getFirstname() +
                            angajatRepository.findById(list.get(0)).get().getLastname();
                    hours = list.get(1);
                    finalList2.add(new HoursPerAngajat(fullName, hours));
                }
                model.addAttribute("listHoursPerUser", finalList2);
            } catch (Exception e){
                model.addAttribute("listHoursPerUser", 0);
                System.out.println(e.getMessage());
            }

            //third

            doubleList = companyRepository.findHoursPerProjectPerUser(company_id);
            List<HoursPerProjectPerAngajat> finalList3 = new ArrayList<>();

            try {
                for (List<Integer> list : doubleList) {
                    project = projectRepository.findById(list.get(0)).get().getName();
                    fullName = angajatRepository.findById(list.get(1)).get().getFirstname() +
                            angajatRepository.findById(list.get(1)).get().getLastname();
                    hours = list.get(2);
                    finalList3.add(new HoursPerProjectPerAngajat(project, fullName, hours));
                }
                model.addAttribute("listHoursPerProjectPerUser", finalList3);
            } catch (Exception e){
                model.addAttribute("listHoursPerProjectPerUser", 0);
                System.out.println(e.getMessage());
            }

        return "company/stats";
    }




//    @GetMapping("companyHome")
//    public Set<Angajat> showAllAngajats(Principal principal) {
//        //int id = 1;
//        String id = principal.getName(); //cui companie
//        Company company = companyRepository.findByCUI(id);
//        List<Project> list = projectRepository.findAllByCompanyId(company.getId());
//        List<Allocation> allocations = allocationRepository.findDistinctByAllocationId_ProjectIn(list);
//        Set<Angajat> angajats = new HashSet<>();
//        for (Allocation temp : allocations) {
//            angajats.add(temp.getAllocationId().getUser());
//        }
//
//        return "companyHome";
//    }


//
//    @GetMapping("/company/hours/project/{param}")
//    public Collection<List<Integer>> getHoursP(@PathVariable int param){
//        return companyRepository.findHoursPerProject(param);
//    }
//    @GetMapping("/company/hours/user/{param}")
//    public Collection<List<Integer>> getHoursU(@PathVariable int param){
//        return companyRepository.findHoursPerUser(param);
//    }
//    @GetMapping("/company/hours/project/user/{param}")
//    public Collection<List<Integer>> getHoursPU(@PathVariable int param){
//        return companyRepository.findHoursPerProjectPerUser(param);
//    }

}
