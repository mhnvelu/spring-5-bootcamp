package com.spring5.projects.petclinic.repositories;

import com.spring5.projects.petclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
