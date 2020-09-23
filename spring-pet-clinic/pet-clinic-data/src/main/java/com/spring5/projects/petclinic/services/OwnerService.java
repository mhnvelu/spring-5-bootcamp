package com.spring5.projects.petclinic.services;

import com.spring5.projects.petclinic.model.Owner;

import java.util.Set;

public interface OwnerService {

    Owner findById(Long Id);

    Owner findByLastName(String lastName);

    Owner save(Owner owner);

    Set<Owner> findAll();

}
