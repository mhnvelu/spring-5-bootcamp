package com.spring5.projects.petclinic.services.map;

import com.spring5.projects.petclinic.model.Vet;
import com.spring5.projects.petclinic.services.CrudService;

import java.util.Set;

public class VetServiceMap extends MapService<Vet, Long> implements CrudService<Vet, Long> {

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet vet) {
        return super.save(vet.getId(), vet);
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet vet) {
        super.delete(vet);
    }
}
