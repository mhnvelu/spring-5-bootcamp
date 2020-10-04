package com.spring5.projects.petclinic.repositories;

import com.spring5.projects.petclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
