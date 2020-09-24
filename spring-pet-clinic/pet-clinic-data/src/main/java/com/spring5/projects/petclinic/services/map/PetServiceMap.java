package com.spring5.projects.petclinic.services.map;

import com.spring5.projects.petclinic.model.Pet;
import com.spring5.projects.petclinic.services.CrudService;

import java.util.Set;

public class PetServiceMap extends MapService<Pet, Long> implements CrudService<Pet, Long> {

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Pet save(Pet pet) {
        return super.save(pet.getId(), pet);
    }

    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pet pet) {
        super.delete(pet);
    }
}