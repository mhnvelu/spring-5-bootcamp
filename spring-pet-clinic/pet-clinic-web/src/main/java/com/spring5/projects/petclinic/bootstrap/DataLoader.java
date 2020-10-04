package com.spring5.projects.petclinic.bootstrap;

import com.spring5.projects.petclinic.model.*;
import com.spring5.projects.petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService,
                      PetTypeService petTypeService, SpecialityService specialityService,
                      VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }

    }

    private void loadData() {
        System.out.println("Loading PetTypes...");

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        System.out.println("Loaded PetTypes...");

        System.out.println("Loading Owners, Pets...");

        Owner owner_1 = new Owner();
        owner_1.setFirstName("Michael");
        owner_1.setLastName("Jordan");
        owner_1.setAddress("1, Big street");
        owner_1.setCity("Big city");
        owner_1.setTelephone("1234567890");

        Pet micPet = new Pet();
        micPet.setName("Rosco");
        micPet.setPetType(savedDogPetType);
        micPet.setOwner(owner_1);
        micPet.setBirthDate(LocalDate.now());
        owner_1.getPets().add(micPet);
        ownerService.save(owner_1);

        Owner owner_2 = new Owner();
        owner_2.setFirstName("Nicholas");
        owner_2.setLastName("Cage");
        owner_2.setAddress("2, Small street");
        owner_2.setCity("Small city");
        owner_2.setTelephone("0987654321");

        Pet nicPet = new Pet();
        nicPet.setName("Meow");
        nicPet.setPetType(savedCatPetType);
        nicPet.setOwner(owner_2);
        nicPet.setBirthDate(LocalDate.now());
        owner_2.getPets().add(nicPet);

        ownerService.save(owner_2);

        Visit catVisit = new Visit();
        catVisit.setPet(nicPet);
        catVisit.setVisitDate(LocalDate.now());
        catVisit.setDescription("Sneezy kitty");

        visitService.save(catVisit);

        System.out.println("Loaded Owners, Pets...");

        System.out.println("Loading Vets, Speciality...");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedSpecialityRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSpecialitySurgery = specialityService.save(surgery);

        Vet vet_1 = new Vet();
        vet_1.setFirstName("Sam");
        vet_1.setLastName("curran");
        vet_1.getSpecialities().add(savedSpecialityRadiology);
        vetService.save(vet_1);

        Vet vet_2 = new Vet();
        vet_2.setFirstName("Jose");
        vet_2.setLastName("Butler");
        vet_2.getSpecialities().add(savedSpecialitySurgery);
        vetService.save(vet_2);

        System.out.println("Loaded Vets, Speciality...");
    }
}
