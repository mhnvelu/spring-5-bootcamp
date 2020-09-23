package com.spring5.projects.petclinic.services;

import com.spring5.projects.petclinic.model.Pet;

import java.util.Set;

public interface PetService {

    Pet findById(Long Id);

    Pet save(Pet pet);

    Set<Pet> findAll();
}
