package com.spring5.projects.petclinic.repositories;

import com.spring5.projects.petclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet,Long> {
}
