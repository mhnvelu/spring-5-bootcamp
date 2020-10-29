package com.spring5.projects.petclinic.controllers;

import com.spring5.projects.petclinic.model.Owner;
import com.spring5.projects.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"/", "/index", "/index.html"})
    public String listOwners(Model model) {
        Set<Owner> owners = ownerService.findAll();
        model.addAttribute("owners", owners);
        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String handleFindOwnerForm(Owner owner, BindingResult bindingResult, Model model) {

        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> owners = ownerService.findByLastNameLike(owner.getLastName());
        if (owners.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (owners.size() == 1) {
            return "redirect:/owners/" + owners.get(0).getId();
        } else {
            model.addAttribute("selections", owners);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView findOwner(@PathVariable("ownerId") String ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject("owner", ownerService.findById(Long.valueOf(ownerId)));
        return modelAndView;
    }

    @GetMapping("/new")
    public String getOwnerCreateForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String handleOwnerCreation(Owner owner) {
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String getUpdateCreateForm(@PathVariable String ownerId, Model model) {
        Owner owner = ownerService.findById(Long.valueOf(ownerId));
        model.addAttribute("owner", owner);
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwner(@PathVariable Long ownerId, Owner owner) {
        owner.setId(ownerId);
        Owner updatedOwner = ownerService.save(owner);
        return "redirect:/owners/" + updatedOwner.getId();
    }

}
