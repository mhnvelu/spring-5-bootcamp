package com.spring5.projects.petclinic.repositories;

import com.spring5.projects.petclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;

public interface SpecialityRepository extends CrudRepository<Speciality, Long> {
}
