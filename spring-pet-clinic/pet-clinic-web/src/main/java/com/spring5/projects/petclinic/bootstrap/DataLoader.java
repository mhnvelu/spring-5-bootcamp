package com.spring5.projects.petclinic.bootstrap;

import com.spring5.projects.petclinic.model.Owner;
import com.spring5.projects.petclinic.model.Pet;
import com.spring5.projects.petclinic.model.PetType;
import com.spring5.projects.petclinic.model.Vet;
import com.spring5.projects.petclinic.services.OwnerService;
import com.spring5.projects.petclinic.services.PetTypeService;
import com.spring5.projects.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService,
                      PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

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
        System.out.println("Loaded Owners, Pets...");

        System.out.println("Loading Vets...");

        Vet vet_1 = new Vet();
        vet_1.setFirstName("Sam");
        vet_1.setLastName("curran");
        vetService.save(vet_1);

        Vet vet_2 = new Vet();
        vet_2.setFirstName("Jose");
        vet_2.setLastName("Butler");
        vetService.save(vet_2);

        System.out.println("Loaded Vets...");
    }
}
