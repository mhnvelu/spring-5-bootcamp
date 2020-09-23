package com.spring5.projects.petclinic.services;

import com.spring5.projects.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
