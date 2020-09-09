package com.example.App.controller;

import com.example.App.Entity.Allocation;
import com.example.App.Entity.Angajat;
import com.example.App.Repository.AllocationRepository;
import com.example.App.Repository.AngajatRepository;
import com.example.App.Repository.ProjectRepository;
import com.example.App.service.AllocationService;
import com.example.App.service.AngajatService;
import com.example.App.util.CrmAllocation;
import com.example.App.util.CrmAllocationEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.util.Calendar;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class AngajatController {

    @Autowired
    AngajatRepository angajatRepository;

    @Autowired
    AngajatService angajatService;

    @Autowired
    AllocationRepository allocationRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AllocationService allocationService;
    int id_user;
    @GetMapping("/angajatHome")
    public String getAllProjects(Model model, Principal principal){
//        System.out.println("\n\n\n\n\n"+principal.getName()+"\n\n\n\n\n");
        Angajat angajat = angajatRepository.findByCNP(principal.getName());
        id_user = angajat.getId();
//        System.out.println("\n\n\n\n\n"+principal.getName()+"\n\n\n\n\n");
//        Angajat angajat = angajatRepository.findById(1);
        model.addAttribute("allocations",
                            allocationRepository.findByAllocationIdUserId(angajat.getId()));
        try {
            model.addAttribute("theWeek",
                    angajatRepository.findTheMostProductiveWeek(angajat.getId()).get(0).get(0));

            model.addAttribute("hoursForWeek",
                    angajatRepository.findTheMostProductiveWeek(angajat.getId()).get(0).get(1));
            model.addAttribute("theProject",
                    projectRepository.findById(
                            angajatRepository.findProjectWithTheMostHours(angajat.getId()).get(0).get(0)).get().getName());
            model.addAttribute("hoursForProject",
                    angajatRepository.findProjectWithTheMostHours(angajat.getId()).get(0).get(1));
        } catch (Exception e) {
            model.addAttribute("theWeek", 0);
            model.addAttribute("hoursForWeek",0);
            model.addAttribute("theProject", 0);
            model.addAttribute("hoursForProject", 0);
            System.out.println(e.getMessage());
        }
        return "angajatHome";
    }

    @GetMapping("/angajat/allocationRegisterForThisUser")
    public String registerAllocation(Model model) {
        model.addAttribute("crmAllocation2", new CrmAllocation());

        return "angajat/registration-formAllocationForThisUser";
    }

    @PostMapping("/angajat/processRegistrationAllocationForThisUserForm")
    public String processReigstrationAllocationForThisUserForm(
            @Valid @ModelAttribute("crmAllocation2") CrmAllocation crmAllocation2,
            Model model) {
        System.out.println("a");
        int id_project = crmAllocation2.getId_project();
//        System.out.println("b");
//        System.out.println(principal.getName());
//        Angajat angajat = angajatRepository.findByCNP(principal.getName());
//        System.out.println("c");
//        int id_user = angajat.getId();
//        System.out.println(id_user);
        System.out.println(id_project);


        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());


        Allocation existing = allocationService.
                findByAllocationId_ProjectIdAndAllocationId_DateAndAllocationId_UserId(id_project, date, id_user);

        if (existing != null) {
            model.addAttribute("crmAllocation", new CrmAllocation());
            model.addAttribute("registrationError", "You can't allocate to this project, because you are not working on it");

            return "angajat/registration-formAllocationForThisUser";
        }
        allocationService.save(crmAllocation2, id_user);

        return "redirect:/angajatHome";
    }

    private int tempProjectId = 0;
    private Date tempDate = null;
    //@RequestMapping(value = "/ex/foos/{fooid}/bar/{barid}", method = GET)
    @RequestMapping(value = "/angajat/edit/{id1}/bar/{id2}", method = GET)
    public String edit(@PathVariable int id1,
                       @PathVariable java.sql.Date id2, Model model) {
        model.addAttribute("crmAllocationEdit", new CrmAllocationEdit());

        tempProjectId = id1;
        tempDate = id2;

        return "angajat/allocationEditForm";
    }

    @PostMapping("/angajat/processAllocationEditForm")
    public String processAllocationEditForm(
            @Valid @ModelAttribute("crmAllocationEdit") CrmAllocationEdit crmAllocationEdit,
            Model model) {

        Allocation allocation = allocationRepository.
            findByAllocationId_ProjectIdAndAllocationId_DateAndAllocationId_UserId(tempProjectId, tempDate, id_user);
        if(allocation == null) {
            model.addAttribute("crmAllocationEdit", new CrmAllocationEdit());
            model.addAttribute("registrationError", "Allocation not found.");

            return "angajat/allocationEditForm";
        }

        allocation.setHours(crmAllocationEdit.getHours());
        allocation.setComments(crmAllocationEdit.getComments());

//        CrmAllocation crmAllocation = null;
//        crmAllocation.setId_project(allocation.getAllocationId().getProject().getId());
//        crmAllocation.setDate(allocation.getAllocationId().getDate());
//        crmAllocation.setCNP(allocation.getAllocationId().getUser().getCNP());
//        crmAllocation.setHours(allocation.getHours());
//        crmAllocation.setComments(allocation.getComments());

        allocationService.save(allocation, tempDate, tempProjectId );

        return "redirect:/angajatHome";
    }

    @GetMapping(value = "angajat/delete/{id1}/bar/{id2}")
    public String delete(@PathVariable int id1, @PathVariable Date id2) {

        allocationService.deleteByAllocationId_DateAndAllocationId_ProjectIdAndAllocationId_UserId(
                id2,id1,id_user);

        return "redirect:/angajatHome";
    }

    /*@GetMapping("/best/project/{param}")
    public Collection<List<Integer>> getBestProject(@PathVariable int param){
        return userRepository.findProjectWithTheMostHours(param);
    }
    @GetMapping("/best/week/{param}")
    public Collection<List<Integer>> getBestWeek(@PathVariable int param){
        return userRepository.findTheMostProductiveWeek(param);
    }*/

}
