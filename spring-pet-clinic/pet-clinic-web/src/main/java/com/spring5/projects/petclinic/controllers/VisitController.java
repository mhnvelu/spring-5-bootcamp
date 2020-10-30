package com.spring5.projects.petclinic.controllers;

import com.spring5.projects.petclinic.model.Pet;
import com.spring5.projects.petclinic.model.Visit;
import com.spring5.projects.petclinic.services.PetService;
import com.spring5.projects.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class VisitController {

    private VisitService visitService;
    private PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);
        Visit visit = Visit.builder().build();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String getPetVisitForm(@PathVariable Long petId, Model model) {
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping(("/owners/{ownerId}/pets/{petId}/visits/new"))
    public String handlePetVisitForm(Visit visit, @PathVariable String ownerId) {
        visitService.save(visit);
        return "redirect:/owners/" + ownerId;
    }
}
