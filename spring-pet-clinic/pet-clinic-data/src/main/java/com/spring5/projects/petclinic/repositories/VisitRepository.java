package com.spring5.projects.petclinic.repositories;

import com.spring5.projects.petclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit,Long> {
}
