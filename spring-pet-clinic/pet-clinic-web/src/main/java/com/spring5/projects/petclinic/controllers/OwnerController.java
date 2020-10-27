package com.spring5.projects.petclinic.controllers;

import com.spring5.projects.petclinic.model.Owner;
import com.spring5.projects.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"/", "/index", "/index.html"})
    public String listOwners(Model model) {
        Set<Owner> owners = ownerService.findAll();
        model.addAttribute("owners", owners);
        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners() {
        return "notimplemented";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView findOwner(@PathVariable("ownerId") String ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject("owner", ownerService.findById(Long.valueOf(ownerId)));
        return modelAndView;
    }

}
