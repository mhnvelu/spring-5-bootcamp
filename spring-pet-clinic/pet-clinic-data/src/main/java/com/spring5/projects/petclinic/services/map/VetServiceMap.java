package com.spring5.projects.petclinic.services.map;

import com.spring5.projects.petclinic.model.Vet;
import com.spring5.projects.petclinic.services.SpecialityService;
import com.spring5.projects.petclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class VetServiceMap extends MapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet vet) {
        if (vet != null) {
            if (vet.getSpecialities().size() > 0) {
                vet.getSpecialities().forEach(speciality -> {
                    if (speciality.getId() == null) {
                        speciality.setId(specialityService.save(speciality).getId());
                    }
                });
            }
            return super.save(vet);
        } else {
            return null;
        }

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
