package com.spring5.projects.petclinic.bootstrap;

import com.spring5.projects.petclinic.model.Owner;
import com.spring5.projects.petclinic.model.Vet;
import com.spring5.projects.petclinic.services.OwnerService;
import com.spring5.projects.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Owners...");

        Owner owner_1 = new Owner();
        owner_1.setId(1L);
        owner_1.setFirstName("Michael");
        owner_1.setLastName("Jordan");
        ownerService.save(owner_1);

        Owner owner_2 = new Owner();
        owner_2.setId(2L);
        owner_2.setFirstName("Nicholas");
        owner_2.setLastName("Cage");
        ownerService.save(owner_2);

        System.out.println("Loaded Owners...");

        System.out.println("Loading Vet...");

        Vet vet_1 = new Vet();
        vet_1.setId(1L);
        vet_1.setFirstName("Sam");
        vet_1.setLastName("curran");
        vetService.save(vet_1);

        Vet vet_2 = new Vet();
        vet_2.setId(2L);
        vet_2.setFirstName("Jose");
        vet_2.setLastName("Butler");
        vetService.save(vet_2);

        System.out.println("Loaded Vet...");

    }
}
