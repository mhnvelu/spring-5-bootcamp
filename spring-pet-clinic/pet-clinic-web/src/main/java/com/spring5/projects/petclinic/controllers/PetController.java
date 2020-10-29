package com.spring5.projects.petclinic.controllers;

import com.spring5.projects.petclinic.model.Owner;
import com.spring5.projects.petclinic.model.Pet;
import com.spring5.projects.petclinic.services.OwnerService;
import com.spring5.projects.petclinic.services.PetService;
import com.spring5.projects.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    PetTypeService petTypeService;
    OwnerService ownerService;
    PetService petService;

    public PetController(PetTypeService petTypeService, OwnerService ownerService,
                         PetService petService) {
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @ModelAttribute("types")
    public Collection<String> populatePetTypes() {
        return petTypeService.findAll().stream().map(petType -> petType.getName())
                .collect(Collectors.toSet());
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder
    public void initOwnerBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String getPetCreationForm(Owner owner, Model model) {
        model.addAttribute("pet", Pet.builder().owner(owner).build());
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/new")
    public String handlePetCreation(Owner owner, Pet pet, BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            petService.save(owner.addPet(pet));
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String getPetUpdateForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/{petId}/edit")
    public String handlePetUpdate(@PathVariable Long petId, Owner owner, Pet pet,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            pet.setId(petId);
            petService.save(owner.addPet(pet));
            return "redirect:/owners/" + owner.getId();
        }
    }

}
