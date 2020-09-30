package com.spring5.projects.petclinic.services.map;

import com.spring5.projects.petclinic.model.PetType;
import com.spring5.projects.petclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PetTypeServiceMap extends MapService<PetType, Long> implements PetTypeService {
    @Override
    public PetType findById(Long id) {
        return super.findById(id);
    }

    @Override
    public PetType save(PetType petType) {
        return super.save(petType);
    }

    @Override
    public Set<PetType> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(PetType petType) {
        super.delete(petType);

    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}